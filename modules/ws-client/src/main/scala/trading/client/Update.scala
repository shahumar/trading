package trading.client

import trading.client.Model.*
import trading.domain.*
import trading.ws.{WsIn, WsOut}
import cats.effect.IO
import cats.syntax.all.*
import retails.catalogue.domain.ProductDto.ProductRequest
import retails.catalogue.domain.{ProductDto, Title, UPC}
import trading.client.Page.{Catalogue, Trading}
import tyrian.*
import tyrian.http.*
import tyrian.cmds.{Dom, Logger}
import tyrian.http.Method.{Get, Post}
import io.circe.syntax.*

import scala.concurrent.duration.*


def disconnected(model: Model): (Model, Cmd[IO, Msg]) =
  model.copy(error="Disconnected from server, please click on Connect".some) -> Cmd.None
  
def refocusInput: Cmd[IO, Msg] =
  Dom.focus("symbol-input")(_.fold(e => Msg.FocusError(ElemId(e.elementId)), _ => Msg.NoOp))
  
  
def runUpdates(model: Model): Msg => (Model, Cmd[IO, Msg]) =
  case Msg.NoOp =>
    model -> Cmd.None
    
  case Msg.FocusError(id) =>
    model.copy(error=s"Fail to focus on ID: ${id.show}".some) -> Cmd.None

  case Msg.ConnStatus(wsMsg) =>
    val (ws, cmd) = model.socket.update(wsMsg)
    model.copy(socket = ws, error=ws.error) -> cmd

  case Msg.CloseAlerts => 
    model.copy(error = None, sub = None, unsub=None) -> refocusInput

  case Msg.SymbolChanged(in) if in.length == 6 =>
    model.copy(symbol = Symbol(in), input=in) -> Cmd.None

  case Msg.SymbolChanged(in) =>
    model.copy(input = in) -> Cmd.None

  case Msg.Subscribe =>
    (model.socket.id, model.symbol) match
      case (_, Symbol.XEMPTY) =>
        model.copy(error = "Invalid symbol".some) -> Cmd.None
      case (Some(_), sl) =>
        val nm = model.copy(sub=sl.some, symbol = mempty, input = mempty)
        nm -> Cmd.Batch(model.socket.publish(WsIn.Subscribe(sl)), refocusInput)
      case (None, _) => 
        disconnected(model)

  case Msg.Unsubscribe(symbol) =>
    model.socket.id.fold(disconnected(model)) { _ =>
      val nm = model.copy(unsub = symbol.some, alerts = model.alerts - symbol)
      nm -> Cmd.Batch(model.socket.publish(WsIn.Unsubscribe(symbol)), refocusInput)
    }

  case Msg.Recv(WsOut.Attached(sid)) =>
    model.socket.id match
      case None =>
        _SocketId.replace(sid.some)(model) -> Cmd.None
      case Some(_) =>
        model -> Cmd.None

  case Msg.Recv(WsOut.OnlineUsers(online)) =>
    model.copy(onlineUsers = online) -> Cmd.None

  case Msg.Recv(WsOut.Notification(t: Alert.TradeAlert)) =>
    model.copy(alerts = model.alerts.updated(t.symbol, t)) -> Cmd.None

  case Msg.Recv(WsOut.Notification(t: Alert.TradeUpdate)) =>
    model.copy(tradingStatus = t.status) -> Cmd.None

  case Msg.NavigateTo(page) => {
    page match
      case Catalogue =>
        val emptyProduct = ProductDto.emptyProduct
        val httpDetails = model.http.copy(url=Some(page.toUrlPath))
        val cmds: Cmd[IO, Msg] =
          Cmd.Batch(
            Logger.info(s"MODEL HERE ${httpDetails} --- ${model.http} URL >>> ${page.toUrlPath}"),
            Cmd.emit(Msg.MakeHttpRequest)
          )
        (model.copy(page=page, http=httpDetails, product=Some(emptyProduct)), cmds)
      case Trading =>
        (model.copy(page=page), Cmd.None)
  }

  case Msg.NavigateToUrl(href) => (model, Nav.loadUrl(href))

  case Msg.MakeHttpRequest =>
    val cmd: Cmd[IO, Msg] =
      model.http.url match
        case None =>
          Logger.info(s"No url entered, skipping Http request. $model ==== ${model.http}")
        case Some(url) =>
          val URL = model.http.baseUrl + url
          Cmd.Batch(
            Logger.info(model.toString),
            Logger.info(s"Making ${model.http.method.asString} request to: $url ${model.http.baseUrl}"),
            Http.send(
              Request(
                model.http.method,
                model.http.headers.map(h => Header(h._1, h._2)),
                URL,
                Body.json(model.http.body),
                model.http.timeout.millis,
                model.http.credentials,
                model.http.cache
              ),
              Decoder(
                Msg.GotHttpResult(_),
                e => Msg.GotHttpError(e.toString)
              )
            )
          )
    (model, cmd)

  case Msg.GotHttpResult(res) =>
    val redirect = model.http.redirect
    redirect match
      case Some(page) =>
        val httpDetails = model.http.copy(method=Get, redirect=None)
        (model.copy(http=httpDetails), Cmd.emit(Msg.NavigateTo(page)))
      case None =>
        (model.copy(http=model.http.copy(response=Option(res), error=None)), Logger.info(s"RESPONSE ---> $res"))

  case Msg.GotHttpError(message) =>
    (model.copy(http = model.http.copy(response=None, error=Option(message))), Logger.info(s"ERROR ---> $message "))

  case Msg.ProductChanged(field, input) =>
    field match
      case "title" =>
        (model.copy(product=Some(ProductRequest(title=Title(input), upc=model.product.get.upc))), Logger.info(input))
      case "upc" =>
        (model.copy(product=Some(ProductRequest(title=model.product.get.title, upc=UPC(input)))), Logger.info(input))
      case _ =>
        (model, Logger.info(s"testing input $input"))

  case Msg.CreateProduct =>
    model.product match
      case Some(product) =>
        val httpDetails = model.http.copy(
          method=Post, 
          url=Some("/product/new"),
          redirect=Some(model.page),
          body=product.asJson.noSpaces)
        (model.copy(http=httpDetails), Cmd.emit(Msg.MakeHttpRequest))
      case None => (model, Logger.info(s"Empty product"))




 

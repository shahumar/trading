package trading.client

import trading.client.Model.*
import trading.domain.*
import trading.ws.{ WsIn, WsOut }

import cats.effect.IO
import cats.syntax.all.*
import tyrian.*
import tyrian.http.*
import tyrian.cmds.{ Dom, Logger }
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
    model.copy(page=page)
    val cmds: Cmd[IO, Msg] =
      Cmd.Batch(
        Cmd.emit(Msg.MakeHttpRequest)
      )
    (model, cmds)
  }

  case Msg.NavigateToUrl(href) => (model, Nav.loadUrl(href))

  case Msg.MakeHttpRequest =>
    val cmd: Cmd[IO, Msg] =
      model.http.url match
        case None =>
          Logger.info("No url entered, skipping Http request.")
        case Some(url) =>
          Cmd.Batch(
            Logger.info(s"Making ${model.http.method.asString} request to: $url"),
            Http.send(
              Request(
                model.http.method,
                model.http.headers.map(h => Header(h._1, h._2)),
                url,
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
    (model.copy(http=model.http.copy(response=Option(res), error=None)), Cmd.None)

  case Msg.GotHttpError(message) =>
    (model.copy(http = model.http.copy(response=None, error=Option(message))), Cmd.None)

 

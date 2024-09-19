package trading.client

import cats.effect.IO
import tyrian.*
import scala.scalajs.js.annotation.*

import scalajs.js

@JSExportTopLevel("TyrianApp")
object WebApp extends TyrianApp[Msg, Model]:

  def router: Location => Msg =
    case loc: Location.Internal => {
      println(s"Unknown route: ${loc.url} --- ${loc.pathName}")
      loc.pathName match
        case "/product" => Msg.NavigateTo(Page.Catalogue)
        case "/trading" => Msg.NavigateTo(Page.Trading)
        case _ =>
          println(s"Unknown route: ${loc.url} --- ${loc.pathName}")
          Msg.NoOp
    }
    case loc: Location.External =>
      Msg.NavigateToUrl(loc.href)


  def init(flags: Map[String, String]): (Model, Cmd[IO, Msg]) =
    Model.init -> Cmd.None
    
  def update(model: Model): Msg => (Model, Cmd[IO, Msg]) =
    runUpdates(model)
    
  def view(model: Model): Html[Msg] =
    render(model)
    
  def subscriptions(model: Model): Sub[IO, Msg] =
    model.socket.subscribe(Subs.ws)

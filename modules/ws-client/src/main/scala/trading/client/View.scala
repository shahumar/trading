package trading.client

import trading.client.ui.*
import trading.domain.*
import cats.syntax.show.*
import retails.catalogue.domain.ProductDto
import tyrian.*
import tyrian.Html.*
import io.circe.parser.decode as jsonDecode

def render(model: Model): Html[Msg] =
  val navItems =
    Page.values.toList.map { pg =>
      if pg == model.page then li(style := CSS.`font-family`("sans-serif"))(pg.toNavLabel)
      else
        li(style := CSS.`font-family`("sans-serif")) {
          a(href := pg.toUrlPath)(pg.toNavLabel)
        }
    }++ List(
      li(style := CSS.`font-family`("sans-serif")) {
        a(href := "#foo")("Foo Link")
      },
      li(style := CSS.`font-family`("sans-serif")) {
        a(href := "127.0.0.1:5000")("Default View")
      }
    )

  val contents =
    model.page match
      case Page.Catalogue =>
        div(`class`:= "content")(
          h2(attribute("align", "center"))(text("Catalogue")),
          div(`class`:="form")(
            div(`class` := "input-group mb-3") (
              input(
                `type` := "text",
                id := "title",
                autoFocus,
                placeholder := "Title",
                onInput(s => Msg.ProductChanged("title", s)),
//                onKeyDown(s => s),
                value := model.product.map(_.title.show).getOrElse("")
              )
            ),
            div(`class` := "input-group mb-3") (
              input(
                `type` := "text",
                id := "upc",
                autoFocus,
                placeholder := "UPC",
                onInput(s => Msg.ProductChanged("upc", s)),
                //                onKeyDown(s => s),
                value := model.product.map(_.upc.show).getOrElse("")
              )
            ),
            div(`class` := "input-group mb-3") (
              button(
                `class` := "btn btn-outline-primary btn-rounded",
                onClick(Msg.CreateProduct)
              )(text("Create Product"))
            )
          ),
          table(`class` := "table table-inverse", hidden(model.http.response.isEmpty))(
            thead(
              tr(
                th("ID"),
                th("Title"),
                th("Slug"),
                th("UPC"),
                th()
              )
            ),
            tbody(
              model.http.response.toList.flatMap(pd => renderProductRow(pd.body))
            )
          )
        )
      case Page.Trading =>
        div(`class`:="content")(
          genericErrorAlert(model),
          subscriptionSuccess(model),
          unsubscriptionSuccess(model),
          h2(attribute("align", "center"))(text("Trading WS")),
          div(`class` := "input-group mb-3") (
            input(
              `type` := "text",
              id := "symbol-input",
              autoFocus,
              placeholder := "Symbol (e.g EURUSD)",
              onInput(s => Msg.SymbolChanged(InputText(s))),
              onKeyDown(subscribeOnEnter),
              value := model.input.value
            ),
            div(`class` := "input-group-append")(
              button(
                `class` := "btn btn-outline-primary btn-rounded",
                onClick(Msg.Subscribe)
              )(text("Subscribe"))
            )
          ),
          div(id := "sid-card", `class` := "card")(
            div(`class` := "sid-body")(
              renderTradeStatus(model.tradingStatus),
              span(" "),
              renderConnectionDetails(model.socket.id, model.onlineUsers)
            )
          ),
          p(),
          table(`class` := "table table-inverse", hidden(model.alerts.isEmpty))(
            thead(
              tr(
                th("Symbol"),
                th("Bid"),
                th("Ask"),
                th("High"),
                th("Low"),
                th("Status"),
                th()
              )
            ),
            tbody(
              model.alerts.toList.flatMap((sl, alt) => renderAlertRow(sl)(alt))
            )
          )
        )

  div(`class` := "container" )(
    div(
      h3(style := CSS.`font-family`("sans-serif"))("Navigation:"),
      ol(navItems)
    ),
    div(br),
    contents
  )

def renderConnectionDetails: (Option[SocketId], Int) => Html[Msg] =

  case (Some(sid), online) =>
    span(
      span(id := "socket-id", `class` := "badge badge-pill badge-success")(text(s"Socket ID: ${sid.show}")),
      span(" "),
      span(id := "online-users", `class` := "badge badge-pill badge-success")(text(s"Online: ${online.show}"))
    )
  case (None, users) =>
    span(
      span(id := "socket-id", `class` := "badge badge-pill badge-secondary")(text("<Disconnected>")),
      span(" "),
      button(`class` := "badge badge-pill badge-primary", onClick(WsMsg.Connecting.asMsg))(text("Connect"))
    )
def renderTradeStatus: TradingStatus => Html[Msg] =
  case TradingStatus.On =>
    span(id := "trade-status", `class` := "badge badge-pill badge-success")(text("Trading ON"))
  case TradingStatus.Off =>
    span(id := "trade-status", `class` := "badge badge-pill badge-danger")(text("Trading Off"))

def alertTypeColumn(imgName: String, value: String): Html[Msg] =
  th(
    img(
      src := s"assets/icons/$imgName.png",
      attribute("width", "28"),
      attribute("height", "28")
    ),
    text(value)
  )

def renderAlertType: AlertType => Html[Msg] =
  case AlertType.StrongBuy =>
    alertTypeColumn("strong-buy", "Strong Buy")

  case AlertType.StrongSell =>
    alertTypeColumn("strong-sell", "Strong Sell")

  case AlertType.Neutral =>
    alertTypeColumn("neutral", "Neutral")

  case AlertType.Sell =>
    alertTypeColumn("sell", "Sell")

  case AlertType.Buy =>
    alertTypeColumn("buy", "Buy")

def renderAlertRow(symbol: Symbol): Alert => List[Html[Msg]] =
  case t: Alert.TradeAlert =>
    List(
      tr(
        th(symbol.show),
        th(t.bidPrice.show),
        th(t.askPrice.show),
        th(t.high.show),
        th(t.low.show),
        renderAlertType(t.alertType),
        th(
          button(
            `class` := "badge badge-pill badge-danger",
            onClick(Msg.Unsubscribe(symbol)),
            title := "Unsubscribe"
          )(
            img(
              src := "assets/icons/delete.png",
              attribute("width", "16"),
              attribute("height", "16")
            )
          )
        )
      )
    )

  case _: Alert.TradeUpdate =>
    List.empty

def subscribeOnEnter: Tyrian.KeyboardEvent => Msg =
  case ev if ev.key == "Enter" => Msg.Subscribe
  case _                       => Msg.NoOp


def renderProductRow(body: String): List[Html[Msg]] = {
  jsonDecode[List[ProductDto.ProductResponse]](body) match
    case Right(products: List[ProductDto.ProductResponse]) =>
        products.map(p => tr(
          th(p.id.show),
          th(p.title.show),
          th(p.slug.show),
          th(p.upc.show),
          th()
        ))
    case Left(error) =>
      List(
        tr(
          th(error.toString),
         ))
}
//  product match
//    case ProductDto.ProductList(id, slug, upc, title) =>
//      List(
//        tr(
//          th(id.show),
//          th(title.show),
//          th(upc.show),
//          th(slug.show),
//          th()
//        )
//      )


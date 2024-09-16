package trading.ws

import trading.domain.*
import cats.Show
import cats.derived.*
import io.circe.Codec

enum WsIn derives Codec.AsObject, Show:
  case Close, Heartbeat
  case Subscribe(symbol: Symbol)
  case Unsubscribe(symbol: Symbol)
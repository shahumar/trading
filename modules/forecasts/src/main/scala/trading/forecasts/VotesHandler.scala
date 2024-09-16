package trading.forecasts

import trading.domain.*
import trading.events.ForecastEvent
import trading.forecasts.store.*
import trading.lib.*
import trading.lib.Consumer.Msg

import cats.effect.MonadCancelThrow
import cats.syntax.all.*

trait VotesHandler[F[_]]:
  def run: Msg[ForecastEvent] => F[Unit]
  
  
object VotesHandler:
  def make[F[_]: Logger: MonadCancelThrow] (
                                           store: ForecastStore[F],
                                           acker: Acker[F, ForecastEvent]
                                           ): VotesHandler[F] = new:
    def run: Msg[ForecastEvent] => F[Unit] =
      case Msg(msgId, _, ForecastEvent.Published(_, _, _, _, _, _)) =>
        acker.ack(msgId)
      case Msg(msgId, _, evt @ ForecastEvent.Voted(_, _, fid, res, _)) =>
        store.tx
          .use { db =>
            db.registerVote(evt) *> db.castVote(fid, res)
          }
          .productR(acker.ack(msgId))
          .handleErrorWith {
            case DuplicateEventId(eid) =>
              Logger[F].error(s"Duplicate event ID: $eid") *> acker.ack(msgId)
            case e => 
              Logger[F].error(s"Failed to register vote for forecast: $fid - ${e.getMessage}") *> acker.nack(msgId)
          }

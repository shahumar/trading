package trading.feed

import java.util.UUID

import scala.concurrent.duration.*

import trading.commands.ForecastCommand
import trading.domain.*
import trading.domain.generators.*
import trading.events.*
import trading.lib.*

import cats.effect.*
import cats.syntax.all.*
import fs2.Stream


object ForecastFeed:
  
  def makeCmdId = CommandId(UUID.randomUUID())
  
  def stream(
            fp: Producer[IO, ForecastCommand],
            fc: Consumer[IO, ForecastEvent],
            ac: Consumer[IO, AuthorEvent]
            ): Stream[IO, Unit] =
    val atEvents = ac.receive.evalMap { case AuthorEvent.Registered(_, cid, aid, _, _, _) =>
      publishCommandGen_(makeCmdId, cid, aid).sample.traverse_ { cmd =>
        IO.println(s">>> $cmd ") *> fp.send(cmd)
      }
    }
    
    val fcEvents = fc.receive.evalMap {
      case ForecastEvent.Published(_, cid, _, fid, _, _) =>
        voteCommandGen_(makeCmdId, cid, fid).sample.traverse_ { cmd =>
          IO.println(s">>> $cmd ") *> fp.send(cmd)
        }
      case _ => IO.unit
    }
    
    val uniqueCmds =
      Stream
        .repeatEval {
          registerCommandGen_(makeCmdId).sample.traverse_(fp.send)
        }
        .metered(2.seconds)
        .interruptAfter(6.seconds)
      
    Stream(uniqueCmds, atEvents, fcEvents).parJoin(3).interruptAfter(10.seconds)
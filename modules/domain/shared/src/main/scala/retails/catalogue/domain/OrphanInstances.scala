package retails.catalogue.domain

import cats.*
import cats.syntax.all.*
import io.circe.{Decoder, Encoder}

import java.time.Instant
import scala.concurrent.duration.{Duration, FiniteDuration}

object OrphanInstances {
  
  given Eq[Instant] = Eq.by(_.getEpochSecond)
  given Order[Instant] = Order.by(_.getEpochSecond)
  given Show[Instant] = Show.show[Instant](_.toString)
  
  given Decoder[FiniteDuration] =
    Decoder[String].emap(s =>
      Duration(s) match
        case fd: FiniteDuration => fd.asRight
        case e => e.toString.asLeft
    )
    
  given Encoder[FiniteDuration] = Encoder[String].contramap(_.toString)

}

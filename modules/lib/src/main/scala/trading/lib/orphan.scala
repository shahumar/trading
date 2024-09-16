package trading.lib

import scala.annotation.nowarn
import monocle.Iso

@nowarn
def eitherUnionIso[E <: Matchable, A <: Matchable]: Iso[Either[E, A], E | A] =
  Iso[Either[E, A], E | A] {
    case Left(e) => e
    case Right(a) => a
  } {
    case e: E => Left(e)
    case a: A => Right(a)
  }



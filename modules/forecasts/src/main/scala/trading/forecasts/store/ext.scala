package trading.forecasts.store

import java.sql.SQLException

import scala.util.control.NoStackTrace

import trading.domain.EventId

import cats.{MonadThrow, ~>}
import cats.effect.kernel.Resource
import cats.syntax.all.*
import doobie.{ ConnectionIO, Transactor }

case object AuthorNotFound extends NoStackTrace
type AuthorNotFound = AuthorNotFound.type 

case object DuplicateForecastError extends NoStackTrace
type DuplicateForecastError = DuplicateForecastError.type 

case object DuplicateAuthorError extends NoStackTrace
type DuplicateAuthorError = DuplicateAuthorError.type 

case object ForecastNotFound extends NoStackTrace
type ForecastNotFound = ForecastNotFound.type 


case class DuplicateEventId(eid: EventId) extends NoStackTrace

extension [F[_]: MonadThrow, A](fa: F[A])
  def onDuplicate(err: Throwable): F[A] =
    fa.adaptError {
      case e: SQLException if e.getSQLState == "23505" => err
    }
  
  def onConstraintViolation(err: Throwable): F[A] =
    fa.adaptError {
      case e: SQLException if e.getSQLState == "23506" => err
    }
    
extension [F[_]: MonadThrow](fa: F[Int])
  def onUpdateFailure(err: Throwable): F[Unit] =
    fa.flatMap {
      case 1 => ().pure[F]
      case _ => err.raiseError[F, Unit]
    }
    
extension [F[_]: DoobieTx](xa: Transactor[F])
  def transaction: Resource[F, ConnectionIO ~> F] =
    DoobieTx[F].transaction(xa)



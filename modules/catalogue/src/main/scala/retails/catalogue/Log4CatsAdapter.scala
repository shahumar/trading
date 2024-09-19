package retails.catalogue

import cats.{Applicative, Monad}
import org.typelevel.log4cats.{LoggerFactory, LoggerName, SelfAwareStructuredLogger}
//import trading.lib.Logger
import org.typelevel.log4cats.Logger

class Log4CatsAdapter[F[_]: Monad](logger: Logger[F]) extends SelfAwareStructuredLogger[F] {
  def error(t: Throwable)(message: => String): F[Unit] = logger.error(s"$message: ${t.getMessage}")
  def warn(t: Throwable)(message: => String): F[Unit] = logger.warn(s"$message: ${t.getMessage}")
  def info(t: Throwable)(message: => String): F[Unit] = logger.info(s"$message: ${t.getMessage}")
  def debug(t: Throwable)(message: => String): F[Unit] = logger.debug(s"$message: ${t.getMessage}")
  def trace(t: Throwable)(message: => String): F[Unit] = logger.debug(s"TRACE: $message: ${t.getMessage}")

  def error(message: => String): F[Unit] = logger.error(message)
  def warn(message: => String): F[Unit] = logger.warn(message)
  def info(message: => String): F[Unit] = logger.info(message)
  def debug(message: => String): F[Unit] = logger.debug(message)
  def trace(message: => String): F[Unit] = logger.debug(s"TRACE: $message")

  override def isTraceEnabled: F[Boolean] = Applicative[F].pure(true)
  override def isDebugEnabled: F[Boolean] = Applicative[F].pure(true)
  override def isInfoEnabled: F[Boolean] = Applicative[F].pure(true)
  override def isWarnEnabled: F[Boolean] = Applicative[F].pure(true)
  override def isErrorEnabled: F[Boolean] = Applicative[F].pure(true)

  override def trace(ctx: Map[String, String])(msg: => String): F[Unit] = logger.debug(s"TRACE: ${msg}")

  override def trace(ctx: Map[String, String], t: Throwable)(msg: => String): F[Unit] = logger.debug(s"TRACE: $msg: ${t.getMessage}")

  override def debug(ctx: Map[String, String])(msg: => String): F[Unit] = logger.debug(msg)

  override def debug(ctx: Map[String, String], t: Throwable)(msg: => String): F[Unit] = logger.debug(s"$msg: ${t.getMessage}")

  override def info(ctx: Map[String, String])(msg: => String): F[Unit] = logger.info(msg)

  override def info(ctx: Map[String, String], t: Throwable)(msg: => String): F[Unit] = logger.info(s"$msg: ${t.getMessage}")

  override def warn(ctx: Map[String, String])(msg: => String): F[Unit] = logger.warn(msg)

  override def warn(ctx: Map[String, String], t: Throwable)(msg: => String): F[Unit] = logger.warn(s"$msg: ${t.getMessage}")

  override def error(ctx: Map[String, String])(msg: => String): F[Unit] = logger.error(msg)

  override def error(ctx: Map[String, String], t: Throwable)(msg: => String): F[Unit] = logger.error(s"$msg: ${t.getMessage}")
}


class CustomLoggerFactory[F[_]: Monad](logger: Logger[F]) extends LoggerFactory[F] {

  override def fromName(name: String): F[SelfAwareStructuredLogger[F]] = 
    Applicative[F].pure(getLogger(LoggerName(name)))

  override def getLogger(implicit name: LoggerName): SelfAwareStructuredLogger[F] =  
    new Log4CatsAdapter(logger)

  override def getLoggerFromName(name: String): SelfAwareStructuredLogger[F] = new Log4CatsAdapter(logger)


}


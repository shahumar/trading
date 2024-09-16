//package retails.catalogue
//
//import cats.effect.Async
//import org.http4s._
//import org.http4s.server._
//import org.http4s.server.middleware._
//import trading.lib.Logger
//import cats.syntax.all._
//
//final case class LoggingMiddleware[F[_]: Async: Logger](routes: HttpRoutes[F]) {
//
//  private val requestLogger: HttpApp[F] => HttpApp[F] = {
//    httpApp =>
//      RequestLogger.httpApp(logHeaders = true, logBody = true)(httpApp)
//  }
//
//  private val responseLogger: HttpApp[F] => HttpApp[F] = {
//    httpApp =>
//      ResponseLogger.httpApp(logHeaders = true, logBody = true)(httpApp)
//  }
//
//  private val loggers: HttpApp[F] => HttpApp[F] = {
//    responseLogger.compose(requestLogger)
//  }
//
//  val httpApp: HttpApp[F] = loggers(routes.orNotFound)
//}

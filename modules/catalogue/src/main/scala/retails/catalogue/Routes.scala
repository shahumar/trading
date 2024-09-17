package retails.catalogue

import cats.effect.{Concurrent}
import cats.effect.kernel.Async
import org.http4s.HttpRoutes
import org.http4s.server.Router
import org.typelevel.log4cats.LoggerFactory
import retails.catalogue.module.Services
import retails.catalogue.routes.ProductRoutes
import trading.lib.Logger


object Routes:
  def make[F[_]: Async](services: Services[F])(using logger: Logger[F]): Routes[F] =
    given LoggerFactory[F] = new CustomLoggerFactory[F](logger)
    new Routes[F](services){}

sealed abstract class Routes[F[_]: Concurrent: Async](services: Services[F])(using loggerFactory: LoggerFactory[F]):
  private val productRoutes = ProductRoutes[F](services.products).routes

  val routes: HttpRoutes[F] = Router(
    version.v1 -> productRoutes
  )





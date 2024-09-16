package retails.catalogue

import cats.effect.Concurrent
import cats.effect.kernel.Async
import org.http4s.HttpRoutes
import org.http4s.server.Router
import org.typelevel.log4cats.{Logger, LoggerFactory}
import retails.catalogue.module.Services
import retails.catalogue.routes.ProductRoutes


object Routes:
  def make[F[_]: Async: Logger: LoggerFactory](services: Services[F]): Routes[F] =
    new Routes[F](services){}

sealed abstract class Routes[F[_]: Concurrent: Async: Logger: LoggerFactory](services: Services[F]):
  private val productRoutes = ProductRoutes[F](services.products).routes

  val routes: HttpRoutes[F] = Router(
    version.v1 -> productRoutes
  )





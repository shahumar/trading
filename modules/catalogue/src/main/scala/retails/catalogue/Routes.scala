package retails.catalogue

import cats.effect.*
import fs2.io.file.Files
import org.http4s.*
import org.http4s
import org.http4s.HttpRoutes
import org.http4s.server.Router
import org.http4s.server.middleware.CORS
import org.typelevel.log4cats.LoggerFactory
import retails.catalogue.module.Services
import retails.catalogue.routes.ProductRoutes
import org.typelevel.log4cats.Logger




object Routes:
  def make[F[_]: Concurrent: Files](services: Services[F])(using logger: Logger[F]): Routes[F] =
    given LoggerFactory[F] = new CustomLoggerFactory[F](logger)
    new Routes[F](services){}

sealed abstract class Routes[F[_]: Concurrent: Logger: Files](services: Services[F])(using loggerFactory: LoggerFactory[F]):

  private val productRoutes = ProductRoutes[F](services.products).routes

  val routes = CORS.policy.withAllowOriginAll.httpRoutes(Router(
      version.v1 -> productRoutes
  ))




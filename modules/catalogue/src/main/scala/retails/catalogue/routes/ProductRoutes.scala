package retails.catalogue.routes

import cats.effect.Concurrent
import cats.syntax.all.*
import io.circe.JsonObject
import org.http4s.*
import io.circe.syntax.*
import org.http4s.server.Router
import org.http4s.dsl.Http4sDsl
import org.http4s.circe.CirceEntityEncoder.*
import retails.catalogue.domain.product.ProductReq
import retails.catalogue.store.ProductStore
import org.http4s.server.middleware.{Logger as Http4sLogger}
import org.typelevel.log4cats.LoggerFactory
import org.typelevel.log4cats.Logger
import retails.catalogue.domain.*



final case class ProductRoutes[F[_]: Concurrent: LoggerFactory](products: ProductStore[F])(using logger: Logger[F]) extends Http4sDsl[F]:
  private val prefix = "/product"


  implicit val eD: EntityDecoder[F, ProductReq] = circe.jsonOf[F, ProductReq]

  private val httpRoutes: HttpRoutes[F] = HttpRoutes.of[F]:
    case GET -> Root =>
      Logger[F].debug("test") >>
      Ok(products.findAll)
    case req @ POST -> Root / "new" =>
      logger.debug("how are you") >>
      req.as[ProductReq].flatMap { pr =>
        val domain = pr.toDomain
        val res = products.save(domain).flatMap { id =>
          Created(JsonObject.singleton("id", id.asJson))
        }
        res
      }

  private val loggingRoutes: HttpRoutes[F] = Http4sLogger.httpRoutes(true, true)(httpRoutes)

  val routes: HttpRoutes[F] = Router(prefix -> loggingRoutes)


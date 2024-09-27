package retails.catalogue.routes

import cats.{Applicative, effect}
import cats.effect.Concurrent
import cats.syntax.all.*
import cats.implicits.*
import io.circe.{Json, JsonObject}
import org.http4s.*
import io.circe.syntax.*
import org.http4s.server.Router
import org.http4s.dsl.Http4sDsl
import org.http4s.circe.CirceEntityEncoder.*
import retails.catalogue.domain.product.ProductReq
import retails.catalogue.store.ProductStore
import org.http4s.server.middleware.Logger as Http4sLogger
import org.typelevel.log4cats.LoggerFactory
import org.typelevel.log4cats.Logger
import retails.catalogue.domain.*
import org.http4s.multipart.Part
import fs2.io.file.{Files, Path as FSPath}




final case class ProductRoutes[F[_]: Concurrent: Applicative: LoggerFactory: Files](products: ProductStore[F])(using logger: Logger[F]) extends Http4sDsl[F]:
  private val prefix = "/product"

  given eD: EntityDecoder[F, ProductReq] = circe.jsonOf[F, ProductReq]
  
  def createDirIfNeeded(filePath: FSPath): F[Unit] =
    filePath.parent match {
      case Some(parentDir) =>
        for {
          exists <- Files[F].exists(parentDir)
          _ <- if (!exists) Files[F].createDirectories(parentDir) else Applicative[F].unit
        } yield ()
      case None => Applicative[F].unit
    }

  private def saveImage(image: Option[Part[F]], upc: String)(using Files[F]): F[Option[FSPath]] = {
    image.traverse { part =>
      val filename = s"${upc}_${part.filename.getOrElse("unknown")}"
      val filePath = FSPath("images") / filename
      val createDir = createDirIfNeeded(filePath)

      createDir >> part
        .body
        .through(Files[F].writeAll(filePath))
        .compile
        .drain
        .as(filePath)
    }
  }

  private val httpRoutes: HttpRoutes[F] = HttpRoutes.of[F]:
    case GET -> Root =>
      Logger[F].debug("test") >>
      Ok(products.findAll)
    case req @ POST -> Root / "new" =>
      EntityDecoder.mixedMultipartResource[F]().use { decoder =>
        req.decodeWith(decoder, strict = true) { multipart =>
          val image = multipart.parts.find(_.name.contains("image"))
          val title = multipart.parts.find(_.name.contains("title"))
          val upc = multipart.parts.find(_.name.contains("upc"))
          (for {
            fileSize <- image.traverse(_.body.compile.count).map(_.getOrElse(0L))
            title <- title.traverse(_.bodyText.compile.string)
            upc <- upc.traverse(_.bodyText.compile.string)
            _ <- logger.debug("how are you")
            savedImage <- title.flatTraverse(t => upc.flatTraverse(u => saveImage(image, u))) 
            response <- (title, upc) match {
              case (Some(t), Some(u)) =>
                val reqData = ProductReq(Title(t), UPC(u))
                products.save(reqData.toDomain).flatMap(id => Created(JsonObject.singleton("id", id.asJson)))
              case _ =>
                BadRequest("Title and UPC are required")
            }
          } yield response).handleErrorWith { error =>
            Logger[F].error(error)("Error processing product creation") >>
              InternalServerError(Json.obj("error" -> "An error occurred while processing your request".asJson))
          }
        }
      }
  
  private val loggingRoutes: HttpRoutes[F] = Http4sLogger.httpRoutes(true, true)(httpRoutes)

  val routes: HttpRoutes[F] = Router(prefix -> loggingRoutes)


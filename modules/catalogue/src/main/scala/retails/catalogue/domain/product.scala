package retails.catalogue.domain

import cats.Show
import cats.derived.*
import cats.effect.IO
import retails.catalogue.domain.*
import io.circe.generic.semiauto.*
import io.circe.{Codec, Decoder, Encoder}
import org.http4s.EntityDecoder
import org.http4s.circe.*

import java.util.UUID

object product {

  final case class Product(
                            id: Id,
                            title: Title,
                            upc: UPC,
                            slug: Slug
                          ) derives Codec.AsObject, Show

  final case class ProductImage(id: Id, productId: Id, path: FilePath)

  case class ProductReq(title: Title, upc: UPC) derives Codec.AsObject, Show:
    def toDomain: Product = {
      val slg = Slug.unsafeApply(title.value.trim.split("\\s+").mkString("-"))
      val id = Id(UUID.randomUUID())
      Product(id, title, upc, slg)

    }

  case class ProductResp(id: UUID, title: String, upc: String, slug: String, path: String) derives Codec.AsObject, Show

  object ProductReq:
    given jsonDecoder: EntityDecoder[IO, ProductReq] = jsonOf[IO, ProductReq]
}



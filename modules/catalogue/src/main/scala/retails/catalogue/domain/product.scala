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
                            id: ProductId,
                            title: Title,
                            upc: UPC,
                            slug: Slug
                          ) derives Codec.AsObject, Show


  case class ProductReq(title: Title, upc: UPC) derives Codec.AsObject, Show:
    def toDomain: Product = {
      val slg = Slug.unsafeApply(title.value.trim.split("\\s+").mkString("-"))
      val id = ProductId.unsafeFrom(UUID.randomUUID().toString)
      Product(id, title, upc, slg)

    }

  object ProductReq:
    given jsonDecoder: EntityDecoder[IO, ProductReq] = jsonOf[IO, ProductReq]
}



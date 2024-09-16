package retails.catalogue.domain

import cats.Show
import cats.derived.*
import cats.effect.IO
import retails.catalogue.domain.*
import doobie.Meta
import io.circe.generic.semiauto.*
import io.circe.{Codec, Decoder, Encoder}
import org.http4s.EntityDecoder
import org.http4s.circe.*

import java.util.UUID

object product {

  type Title = StringType
  type UPC = StringType
  type Slug = SlugType
  type DateCreated = Timestamp
  type DateUpdated = Timestamp


  type ProductId = ProductId.Type
  object ProductId extends IdNewtype:
    def apply(value: String): ProductId = unsafeFrom(value)
    given idMeta: Meta[ProductId] = Meta[String].imap(unsafeFrom(_))(_.value.toString)

  object Title:
    def apply(value: String): Title = StringType(value)

  object UPC:
    def apply(value: String): UPC = StringType(value)
  // You might want to add UPC-specific validation here

  object Slug:
    def apply(value: String): Slug = SlugType.fromString(value) match
      case Right(slg) => slg
      case Left(error) => throw new IllegalArgumentException(error)

    def unsafeApply(value: String): Slug = SlugType.unsafeFrom(value)



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



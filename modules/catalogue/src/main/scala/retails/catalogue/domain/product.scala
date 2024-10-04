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

  object Domain:
    final case class Product(
                              id: Id,
                              title: Title,
                              upc: UPC,
                              slug: Slug
                            ) derives Codec.AsObject, Show

    final case class ProductImage(id: Id, productId: Id, path: FilePath)
    final case class ProductPrice(id: Id, productId: Id, price: Price, isDefault: Boolean)


  object DTO:
    import Domain.*
    case class ProductRequestDTO(title: Title, upc: UPC, price: Price) derives Codec.AsObject, Show:
      def toProduct: Product = {
        val slg = Slug.unsafeApply(title.value.trim.split("\\s+").mkString("-"))
        val id = Id(UUID.randomUUID())
        Product(id, title, upc, slg)
      }
      
      def toProductPrice(productId: Id): ProductPrice =
        ProductPrice(id=Id(UUID.randomUUID()), productId = productId, price = price, isDefault = true)
        
    case class ProductResponseDTO(
                            id: UUID,
                            title: String,
                            upc: String,
                            slug: String,
                            path: String
                          ) derives Codec.AsObject, Show
    object ProductRequestDTO:
      given jsonDecoder: EntityDecoder[IO, ProductRequestDTO] = jsonOf[IO, ProductRequestDTO]
}



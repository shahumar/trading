package retails.catalogue.domain

import cats.derived.*
import cats.syntax.all.*
import cats.Show
import io.circe.generic.semiauto.{deriveDecoder, deriveEncoder}
import io.circe.{Codec, Decoder, Encoder}

enum ProductDto derives Codec.AsObject, Show:
  case ProductResponse(
                   id: ProductId,
                   slug: Slug,
                   upc: UPC,
                   title: Title)
  case ProductRequest(title: Title, upc: UPC)

object ProductDto:
  given Decoder[ProductDto.ProductResponse] = deriveDecoder[ProductDto.ProductResponse]
  given Encoder[ProductDto.ProductRequest] = deriveEncoder[ProductDto.ProductRequest]
  
  def emptyProduct: ProductRequest = ProductRequest(Title(""), UPC(""))


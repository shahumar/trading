package retails.catalogue.domain

import cats.derived.*
import cats.syntax.all.*
import cats.Show
import io.circe.Codec

enum ProductDto derives Codec.AsObject, Show:
  def id: ProductId
  def slug: Slug
  def upc: UPC
  def title: Title
  case ProductList(
                   id: ProductId,
                   slug: Slug,
                   upc: UPC,
                   title: Title)

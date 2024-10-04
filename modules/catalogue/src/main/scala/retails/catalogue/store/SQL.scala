package retails.catalogue.store

import java.util.UUID
import doobie.*
import doobie.implicits.*
import cats.syntax.show.*
import retails.catalogue.domain.*
import retails.catalogue.domain.product.Domain.*
import retails.catalogue.domain.product.DTO.*

object SQL:

  given Meta[UUID] = Meta[String].imap[UUID](UUID.fromString)(_.toString)

  given Read[Product] = Read[(UUID, String, String, String)].map {
    (id, title, upc, slug) =>
      Product(Id(id.toString), Title(title), UPC(upc), Slug(slug))
  }

  val selectProduct: Id => Query0[Product] = id =>
    sql"""
         SELECT a.id, a.title, a.upc, a.slug FROM products AS a
         WHERE a.id=${id.show}
         """.query[Product]

  val findAll: Query0[ProductResponseDTO] =
    sql"""
         SELECT p.id, p.title, p.upc, p.slug, pi.path FROM products as p
         INNER JOIN product_images pi ON pi.product_id = p.id
       """.query[ProductResponseDTO]

  val insertproduct: Product => ConnectionIO[UUID] = p =>
    sql"""
         INSERT INTO products (id, title, upc, slug)
         VALUES (uuid(${p.id.show}), ${p.title.value}, ${p.upc.value}, ${p.slug.value})
         """.update.withUniqueGeneratedKeys[UUID]("id")
    
  val insertImage: ProductImage => Update0 = pi =>
    sql"""
         INSERT INTO product_images (id, product_id, path)
         VALUES (uuid(${pi.id.show}), uuid(${pi.productId.show}), ${pi.path.value})
       """.update
    
  val insertProductPrice: ProductPrice => ConnectionIO[UUID] = p =>
    sql"""
         INSERT INTO product_price (id, product_id, price, is_default)
         VALUES (uuid(${p.id.show}), uuid(${p.productId.show}), ${p.price.value}, ${p.isDefault}) 
       """.update.withUniqueGeneratedKeys[UUID]("id")


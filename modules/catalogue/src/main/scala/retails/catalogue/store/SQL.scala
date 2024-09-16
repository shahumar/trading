package retails.catalogue.store

import java.util.UUID
import doobie.*
import doobie.implicits.*
import cats.syntax.show.*
import retails.catalogue.domain.product.*

object SQL:

  given Meta[UUID] = Meta[String].imap[UUID](UUID.fromString)(_.toString)

  given Read[Product] = Read[(UUID, String, String, String)].map {
    (id, title, upc, slug) =>
      Product(ProductId(id.toString), Title(title), UPC(upc), Slug(slug))
  }

  val selectProduct: ProductId => Query0[Product] = id =>
    sql"""
         SELECT a.id, a.title, a.upc, a.slug FROM products AS a
         WHERE a.id=${id.show}
         """.query[Product]

  val findAll: Query0[Product] =
    sql"""
         SELECT id, title, upc, slug FROM products
       """.query[Product]

  val insertproduct: Product => Update0 = p =>
    sql"""
         INSERT INTO products (id, title, upc, slug)
         VALUES (${p.id.value}, ${p.title.value}, ${p.upc.value}, ${p.slug.value})
         """.update


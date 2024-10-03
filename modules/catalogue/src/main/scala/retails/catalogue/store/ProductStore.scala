package retails.catalogue.store

import cats.effect.kernel.MonadCancelThrow
import doobie.Transactor
import doobie.implicits.*
import retails.catalogue.domain.*
import retails.catalogue.domain.product.*

import java.util.UUID



trait ProductStore[F[_]]:
  def fetch(id: Id): F[Option[Product]]
  def findAll: F[List[ProductResp]]
  def save(product: Product): F[Id]
  def saveImage(id: Id, path: String): F[Id]


trait TxProductStore[F[_]]:
  def save(product: Product): F[Unit]


object ProductStore:
  def from[F[_]: DoobieTx: MonadCancelThrow](xa: Transactor[F]): ProductStore[F] =
    new:
      def fetch(id: Id): F[Option[product.Product]] =
        SQL.selectProduct(id).option.transact(xa)

      def findAll: F[List[ProductResp]] =
        SQL.findAll.to[List].transact(xa)

      def save(product: Product): F[Id] =
        SQL.insertproduct(product).withUniqueGeneratedKeys[Id]("id").transact(xa)

      def saveImage(id: Id, path: String): F[Id] =
        val image = ProductImage(Id(UUID.randomUUID()), id, FilePath(path))
        SQL.insertImage(image).withUniqueGeneratedKeys[Id]("id").transact(xa)




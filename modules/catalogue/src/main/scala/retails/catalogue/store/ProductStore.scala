package retails.catalogue.store

import cats.effect.kernel.{MonadCancelThrow}
import doobie.{Transactor}
import doobie.implicits.*
import retails.catalogue.domain.*
import retails.catalogue.domain.product.*


trait ProductStore[F[_]]:
  def fetch(id: ProductId): F[Option[Product]]
  def findAll: F[List[Product]]
  def save(product: Product): F[ProductId]


trait TxProductStore[F[_]]:
  def save(product: Product): F[Unit]


object ProductStore:
  def from[F[_]: DoobieTx: MonadCancelThrow](xa: Transactor[F]): ProductStore[F] =
    new:
      def fetch(id: ProductId): F[Option[Product]] =
        SQL.selectProduct(id).option.transact(xa)

      def findAll: F[List[Product]] =
        SQL.findAll.to[List].transact(xa)

      def save(product: Product): F[ProductId] =
        SQL.insertproduct(product).withUniqueGeneratedKeys[ProductId]("id").transact(xa)


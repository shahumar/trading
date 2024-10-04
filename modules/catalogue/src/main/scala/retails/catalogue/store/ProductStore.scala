package retails.catalogue.store

import cats.effect.kernel.MonadCancelThrow
import doobie.Transactor
import doobie.implicits.*
import retails.catalogue.domain.*
import retails.catalogue.domain.product.Domain.*
import retails.catalogue.domain.product.DTO.{ProductRequestDTO, ProductResponseDTO}

import java.util.UUID



trait ProductStore[F[_]]:
  def fetch(id: Id): F[Option[Product]]
  def findAll: F[List[ProductResponseDTO]]
  def save(product: ProductRequestDTO): F[Id]
  def saveImage(id: Id, path: String): F[Id]


trait TxProductStore[F[_]]:
  def save(product: Product): F[Unit]


object ProductStore:
  def from[F[_]: DoobieTx: MonadCancelThrow](xa: Transactor[F]): ProductStore[F] =
    new:
      def fetch(id: Id): F[Option[Product]] =
        SQL.selectProduct(id).option.transact(xa)

      def findAll: F[List[ProductResponseDTO]] =
        SQL.findAll.to[List].transact(xa)

      def save(dto: ProductRequestDTO): F[Id] =
        val response = for {
          p <- SQL.insertproduct(dto.toProduct) //.withUniqueGeneratedKeys[Id]("id")
          pid = Id(p)
          i <- SQL.insertProductPrice(dto.toProductPrice(pid))
        } yield pid
        response.transact(xa)

      def saveImage(id: Id, path: String): F[Id] =
        val image = ProductImage(Id(UUID.randomUUID()), id, FilePath(path))
        SQL.insertImage(image).withUniqueGeneratedKeys[Id]("id").transact(xa)




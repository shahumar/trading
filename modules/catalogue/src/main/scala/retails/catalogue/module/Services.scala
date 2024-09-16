package retails.catalogue.module

import cats.effect.kernel.Temporal
import doobie.util.transactor.Transactor
import retails.catalogue.store.{DoobieTx, ProductStore}
import trading.lib.GenUUID


object Services:
  def make[F[_]: GenUUID: Temporal: DoobieTx](xa: Transactor[F]): Services[F] =
    new Services[F](
      products = ProductStore.from[F](xa)
    ) {}


sealed abstract class Services[F[_]] private(
    val products: ProductStore[F])

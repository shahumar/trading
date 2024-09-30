package retails.catalogue.module

import cats.effect.kernel.Temporal
import doobie.util.transactor.Transactor
import fs2.io.file.Files
import retails.catalogue.services.ImageStore
import retails.catalogue.store.{DoobieTx, ProductStore}
import trading.lib.GenUUID


object Services:
  def make[F[_]: Files: GenUUID: Temporal: DoobieTx](xa: Transactor[F]): Services[F] =
    new Services[F](
      products = ProductStore.from[F](xa),
      imageService = ImageStore.from[F]
    ) {}


sealed abstract class Services[F[_]] private(
    val products: ProductStore[F], val imageService: ImageStore[F])

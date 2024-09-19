package retails.catalogue.services

import retails.catalogue.domain.*
import retails.catalogue.domain.product.*

trait Products[F[_]]:
  def findAll: F[List[Product]]
  def create(title: Title, upc: UPC): F[ProductId]



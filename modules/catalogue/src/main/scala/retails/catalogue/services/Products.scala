package retails.catalogue.services

import retails.catalogue.domain.*

trait Products[F[_]]:
  def findAll: F[List[Product]]
  def create(title: Title, upc: UPC): F[Id]



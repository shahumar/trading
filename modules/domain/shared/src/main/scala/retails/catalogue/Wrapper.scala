package retails.catalogue.domain

import monocle.Iso

trait Wrapper[A, B]:
  def iso: Iso[A, B]

object Wrapper:
  def apply[A, B](using ev: Wrapper[A, B]): Wrapper[A, B] = ev 

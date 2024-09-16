package retails.catalogue.domain

import cats.{Eq, Order, Show}
import io.circe.{Decoder, Encoder}
import monocle.Iso

import java.util.UUID


abstract class NewTypeWithoutEq[A]:

  opaque type Type = A
  inline def apply(a: A): Type = a
  protected inline final def derive[F[_]](using ev: F[A]): F[Type] = ev
  extension (t: Type) inline def value: A = t
  given Wrapper[A, Type] with
    def iso: Iso[A, Type] = Iso[A, Type](apply(_))(_.value)


abstract class Newtype[A](using eqv: Eq[A], ord: Order[A], shw: Show[A], enc: Encoder[A], dec: Decoder[A]):
  opaque type Type = A
  inline def apply(a: A): Type = a
  protected inline final def derive[F[_]](using ev: F[A]): F[Type] = ev
  extension (t: Type) inline def value: A = t
  given Wrapper[A, Type] with
    def iso: Iso[A, Type] = Iso[A, Type](apply(_))(_.value)
  
  given Eq[Type] = eqv
  given Order[Type] = ord
  given Show[Type] = shw
  given Encoder[Type] = enc
  given Decoder[Type] = dec
  given Ordering[Type] = ord.toOrdering
  
  
abstract class IdNewtype extends Newtype[UUID]:
  given IsUUID[Type] = derive[IsUUID]
  def unsafeFrom(str: String): Type = apply(UUID.fromString(str))


abstract class SlugNewType extends Newtype[String]:
  def validate(slug: String): Boolean = slug.matches("^[a-zA-Z0-9-_]+$")

  def fromString(str: String): Either[String, Type] =
    if(validate(str)) Right(apply(str))
    else Left(s"Invalid slug fromat: $str")

  def unsafeFrom(str: String): Type = fromString(str) match
    case Right(slug) => slug
    case Left(error) => throw new IllegalArgumentException(error)

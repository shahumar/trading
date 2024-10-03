package retails.catalogue.domain

import doobie.Meta
import io.circe.generic.semiauto.*

import java.time.Instant
import cats.{Eq, Show}
import retails.catalogue.domain.OrphanInstances.given

import java.util.UUID


type Timestamp = Timestamp.Type
object Timestamp extends Newtype[Instant]

type StringType = StringType.Type
object StringType extends Newtype[String]

type SlugType = SlugType.Type
object SlugType extends SlugNewType


type Title = StringType
type UPC = StringType
type Slug = SlugType
type DateCreated = Timestamp
type DateUpdated = Timestamp


object FilePath extends Newtype[String]
type FilePath = FilePath.Type

type Id = Id.Type
object Id extends IdNewtype:
  def apply(value: String): Id = unsafeFrom(value)
  given Meta[UUID] = Meta[String].imap(UUID.fromString)(_.toString)
  given Meta[Id] = Meta[UUID].imap(Id.apply)(_.value)

object Title:
  def apply(value: String): Title = StringType(value)
  def unapply(title: Title): String = title.value

object UPC:
  def apply(value: String): UPC = StringType(value)

  def unapply(upc: UPC): String = upc.value
  

object Slug:
  def apply(value: String): Slug = SlugType.fromString(value) match
    case Right(slg) => slg
    case Left(error) => throw new IllegalArgumentException(error)

  def unsafeApply(value: String): Slug = SlugType.unsafeFrom(value)






package retails.catalogue.domain

import doobie.Meta
import io.circe.generic.semiauto.*
import java.time.Instant
import cats.{Eq, Show}
import retails.catalogue.domain.OrphanInstances.given


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

type ProductId = ProductId.Type

object ProductId extends IdNewtype:
  def apply(value: String): ProductId = unsafeFrom(value)

  given idMeta: Meta[ProductId] = Meta[String].imap(unsafeFrom)(_.value.toString)

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






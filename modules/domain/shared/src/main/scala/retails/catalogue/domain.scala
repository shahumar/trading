package retails.catalogue.domain

import java.time.Instant
import cats.{Eq, Show}
import retails.catalogue.domain.OrphanInstances.given


type Timestamp = Timestamp.Type
object Timestamp extends Newtype[Instant]

type StringType = StringType.Type
object StringType extends Newtype[String]

type SlugType = SlugType.Type
object SlugType extends SlugNewType






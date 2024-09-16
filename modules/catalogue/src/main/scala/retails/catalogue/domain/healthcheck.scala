//package retails.catalogue.domain
//
//import cats.derived.*
//import cats.*
//import io.circe.{Codec, Encoder, Json}
//import monocle.Iso
//
//object healthcheck:
//
//  enum Status derives CanEqual:
//    case Okay, Unreachable
//
//  object Status:
//    given Encoder[Status] = Encoder.instance {
//      case Okay => Json.obj("status" -> Json.fromString("Okay"))
//      case Unreachable => Json.obj("status" -> Json.fromString("Unreachable"))
//    }
//
//    val _Bool: Iso[Status, Boolean] =
//      Iso[Status, Boolean] {
//        case Okay => true
//        case Unreachable => false
//      }(if (_) Okay else Unreachable)
//
//
//  opaque type RedisStatus = Status
//  object RedisStatus:
//    def apply(status: Status): RedisStatus = status
//
//  opaque type PostgresStatus = Status
//  object PostgresStatus:
//    def apply(status: Status): PostgresStatus = status
//
//
//
//  case class AppStatus(redis: RedisStatus, postgres: PostgresStatus) derives Codec.AsObject
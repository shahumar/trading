//package retails.catalogue.domain.auth.routes
//
//import auth.domain.*
//import cats.MonadThrow
//import org.http4s.dsl.Http4sDsl
//import cats.syntax.all.*
//import org.http4s.*
//import org.http4s.circe.CirceEntityEncoder.*
//import org.http4s.dsl.Http4sDsl
//import org.http4s.server.Router
//
//final case class LoginRoutes[F[_]: JsonDecoder: MonadThrow](auth: Auth[F]) extends Http4sDsl[F] {
//
//  private[catalogue] val prefix = "/auth"
//
//  private val httpRoutes: HttpRoutes[F] = HttpRoutes.of[F] {
//    case req @ POST -> Root / "login" =>
//      req.decodeR[LoginUser] { user =>
//        auth
//          .login(user.username.toDomain, user.password.toDomain)
//          .flatMap(Ok(_))
//          .recoverWith {
//            case UserNotFound(_) | InvalidPassword(_) => Forbidden()
//          }
//      }
//  }
//
//  val routes: HttpRoutes[F] = Router(prefix -> httpRoutes)
//}

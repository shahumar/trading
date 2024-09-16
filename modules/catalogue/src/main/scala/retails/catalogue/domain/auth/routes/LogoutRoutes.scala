//package retails.catalogue.domain.auth.routes
//
//import retails.catalogue.domain.auth.users.*
//import retails.catalogue.services.*
//
//import cats.Monad
//import cats.syntax.all._
//import dev.profunktor.auth.AuthHeaders
//import org.http4s.{AuthedRoutes, HttpRoutes}
//import org.http4s.*
//import org.http4s.dsl.Http4sDsl
//import org.http4s.server.{AuthMiddleware, Router}
//
//final case class LogoutRoutes[F[_]: Monad](auth: Auth[F]) extends Http4sDsl[F]:
//  private [catalogue] val prefixPath = "/auth"
//
//  private val httpRoutes: AuthedRoutes[CommonUser, F] = AuthedRoutes.of {
//    case ar @ POST -> Root / "logout" as user =>
//      AuthHeaders
//        .getBearerToken(ar.req)
//        .traverse_(auth.logout(_, user.value.name)) *> NoContent()
//  }
//
//  def routes(authMiddleware: AuthMiddleware[F, CommonUser]): HttpRoutes[F] = Router(
//    prefixPath -> authMiddleware(httpRoutes)
//  )
//

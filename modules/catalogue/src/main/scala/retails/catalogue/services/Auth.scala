//package retails.catalogue.services
//
//
//import auth.domain.{ *, given }
//
//import retails.catalogue.domain.auth.users.AdminUser
//
//import cats._
//import cats.implicits.given
//import cats.syntax.eq._
//import dev.profunktor.auth.jwt.JwtToken
//import dev.profunktor.redis4cats.RedisCommands
//import io.circe.syntax._
//import pdi.jwt.JwtClaim
//
//
//trait Auth[F[_]]:
//  def newUser(username: Username, password: Password): F[JwtToken]
//  def login(username: Username, password: Password): F[JwtToken]
//  def logout(token: JwtToken, username: Username): F[Unit]
//
//
//trait UsersAuth[F[_], A]:
//  def findUser(token: JwtToken)(claim: JwtClaim): F[Option[A]]
//
//object UsersAuth:
//
//  def admin[F[_]: Applicative](adminToken: JwtToken, adminUser: AdminUser): UsersAuth[F, AdminUser] =
//    new UsersAuth[F, AdminUser]:
//      override def findUser(token: JwtToken)(claim: JwtClaim): F[Option[AdminUser]] =
//        (token === adminToken)
//          .pure[F]
//          .map(isAdmin => Option.when(isAdmin)(adminUser))
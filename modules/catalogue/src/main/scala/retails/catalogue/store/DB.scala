package retails.catalogue.store

import cats.effect.kernel.{Async, Resource}
import doobie.ExecutionContexts
import doobie.hikari.HikariTransactor
import org.flywaydb.core.Flyway
import retails.catalogue.PostgreSQLConfig

object DB:
  
  def init[F[_]: Async](cfg: PostgreSQLConfig): Resource[F, HikariTransactor[F]] =
    for {
      ce <- ExecutionContexts.fixedThreadPool[F](32)
      xa <- HikariTransactor.newHikariTransactor[F](
        "org.postgresql.Driver",
        s"jdbc:postgresql:${cfg.database}",
        cfg.user,
        cfg.password,
        ce
      )
      _ <- Resource.eval(xa.configure { ds =>
        Async[F].pure(Flyway.configure().dataSource(ds).load().migrate())
      })
    } yield xa

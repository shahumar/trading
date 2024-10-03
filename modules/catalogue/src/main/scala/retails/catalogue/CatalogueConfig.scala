package retails.catalogue

import cats.effect.kernel.Async
import cats.syntax.all.*
import ciris.*
import com.comcast.ip4s.*
import trading.domain.{ given }


final case class CatalogueConfig(httpPort: Port, host: Host, database: PostgreSQLConfig )
final case class PostgreSQLConfig(host: Host, port: Port, user: String, password: String, database: String, max: Int)

object Config:

  def load[F[_]: Async]: F[CatalogueConfig] =
    (
      env("HTTP_PORT").as[Port].default(port"5000"),
      env("HTTP_HOST").as[Host].default(host"localhost")
    ).parMapN { (port, host) =>
      CatalogueConfig(
        httpPort = port,
        host = host,
        database = PostgreSQLConfig(
          host = host"localhost",
          port = port"5432",
          user = "retails",
          password = "retails",
          database = "retails",
          max = 10
        )
      )
    }.load[F]

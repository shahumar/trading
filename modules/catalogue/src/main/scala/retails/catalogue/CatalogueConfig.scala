package retails.catalogue

import cats.effect.kernel.Async
import cats.syntax.all.*
import ciris.*
import com.comcast.ip4s.*
import trading.domain.{ given }


final case class CatalogueConfig(httpPort: Port, host: Host )

object Config:

  def load[F[_]: Async]: F[CatalogueConfig] =
    (
      env("HTTP_PORT").as[Port].default(port"5000"),
      env("HTTP_HOST").as[Host].default(host"localhost")
    ).parMapN { (port, host) =>
      CatalogueConfig(port, host)
    }.load[F]

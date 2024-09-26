package retails.catalogue

import fs2.Stream
import cats.effect.{IO, IOApp, Resource}
import retails.catalogue.module.Services
import retails.catalogue.store.DB
import trading.core.http.Ember
//import trading.lib.Logger
import org.typelevel.log4cats.Logger
import org.typelevel.log4cats.slf4j.Slf4jLogger


object Main extends IOApp.Simple:

  given Logger[IO] = Slf4jLogger.getLogger[IO]

  override def run: IO[Unit] =
    Stream
      .resource(resources)
      .flatMap(server => Stream.eval(server.useForever))
      .compile
      .drain


  def resources =
    for
      config <- Resource.eval(Config.load[IO])
      _ <- Resource.eval(Logger[IO].info("Initializing catalogue service"))
      xa <- DB.init[IO]
      services = Services.make[IO](xa)
      apiApp = Routes.make[IO](services).routes
      routes <- Resource.eval(apiApp)
      server = Ember.routes[IO](config.httpPort, routes)
    yield server
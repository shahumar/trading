package retails.catalogue

import fs2.Stream
import cats.effect.{IO, IOApp, Resource}
import retails.catalogue.module.Services
import retails.catalogue.store.DB
import trading.core.http.Ember
import trading.lib.Logger



object Main extends IOApp.Simple:

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
      routes = Routes.make[IO](services).routes
      server = Ember.routes[IO](config.httpPort, routes)
    yield server
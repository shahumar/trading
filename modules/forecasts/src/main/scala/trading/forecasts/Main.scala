package trading.forecasts

import trading.commands.ForecastCommand
import trading.core.AppTopic
import trading.core.http.Ember
import trading.events.*
import trading.forecasts.cdc.*
import trading.forecasts.store.*
import trading.lib.*

import cats.effect.*
import cats.syntax.all.*
import dev.profunktor.pulsar.{ Producer as PulsarProducer, Pulsar, Subscription, Topic}
import fs2.Stream

object Main extends IOApp.Simple:

  def run: IO[Unit] =
    Stream
      .resource(resources)
      .flatMap { (server, cmdConsumer, evtConsumer, outConsumer, handler, outbox, cdc, engine) =>
        Stream.eval(server.useForever).concurrently {
          Stream(
            cmdConsumer.receiveM.evalMap(engine.run),
            outConsumer.receiveM.evalMap(outbox.run),
            evtConsumer.receiveM.evalMap(handler.run),
            cdc
          ).parJoin(5)
        }
      }
      .compile
      .drain

  def settings[A](name: String) =
    PulsarProducer
      .Settings[IO, A]()
      .withDeduplication
      .withName(s"fc-$name-event")
      .some

  val sub =
    Subscription.Builder
      .withName("forecasts")
      .withType(Subscription.Type.Failover)
      .build
    
  def cdcResources(pulsar: Pulsar.T, topic: Topic.Single): Resource[IO, Stream[IO, Unit]] =
    Producer.pulsar[IO, OutboxEvent](pulsar, topic, settings("outbox")).map { p =>
      OutboxProducer.make(p).stream
    }

  def resources =
    for
      config <- Resource.eval(Config.load[IO])
      pulsar <- Pulsar.make[IO](config.pulsar.url, Pulsar.Settings().withTransactions)
      _ <- Resource.eval(Logger[IO].info("Initializing forecasts service"))
      cmdTopic = AppTopic.ForecastCommands.make(config.pulsar)
      authorTopic = AppTopic.AuthorEvents.make(config.pulsar)
      forecastTopic = AppTopic.ForecastEvents.make(config.pulsar)
      outboxTopic = AppTopic.OutboxEvents.make(config.pulsar)
      authors <- Producer.pulsar[IO, AuthorEvent](pulsar, authorTopic, settings("author"))
      forecasts <- Producer.pulsar[IO, ForecastEvent](pulsar, forecastTopic, settings("forecast"))
      cmdConsumer <- Consumer.pulsar[IO, ForecastCommand](pulsar, cmdTopic, sub)
      evtConsumer <- Consumer.pulsar[IO, ForecastEvent](pulsar, forecastTopic, sub)
      outConsumer <- Consumer.pulsar[IO, OutboxEvent](pulsar, outboxTopic, sub)
      cdcEvents <- cdcResources(pulsar, outboxTopic)
      xa <- DB.init[IO]
      atStore = AuthorStore.from(xa)
      fcStore = ForecastStore.from(xa)
      server = Ember.default[IO](config.httpPort)
      engine = Engine.make(forecasts, atStore, fcStore, cmdConsumer)
      handler = VotesHandler.make(fcStore, evtConsumer)
      outbox = OutboxHandler.make(authors, forecasts, outConsumer)
    yield (server, cmdConsumer, evtConsumer, outConsumer, handler, outbox, cdcEvents, engine)

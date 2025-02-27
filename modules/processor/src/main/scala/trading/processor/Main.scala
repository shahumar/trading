package trading.processor

import trading.commands.*
import trading.core.AppTopic
import trading.core.http.Ember
import trading.domain.AppId
import trading.events.*
import trading.lib.*
import trading.state.TradeState

import cats.effect.*
import cats.syntax.all.*
import dev.profunktor.pulsar.{ Config as _, Consumer as PulsarConsumer, Producer as PulsarProducer, * }
import fs2.Stream

object Main extends IOApp.Simple:

  def run: IO[Unit] =
    Stream
      .resource(resources)
      .flatMap { (server, trConsumer, swConsumer, fsm) =>
        Stream.eval(server.useForever).concurrently {
          trConsumer.receiveM
            .either(swConsumer.receiveM)
            .evalMapAccumulate(TradeState.empty)(fsm.run)
        }
      }
      .compile
      .drain


  def cmdSub(id: AppId) =
    Subscription.Builder
      .withName(id.name)
      .withType(Subscription.Type.KeyShared)
      .build

  def swtSub(id: AppId) =
    Subscription.Builder
      .withName(id.show)
      .withType(Subscription.Type.Exclusive)
      .build

  val compact =
    PulsarConsumer.Settings[IO, SwitchCommand]().withReadCompacted.some

  val evtSettings =
    PulsarProducer
      .Settings[IO, TradeEvent]()
      .withDeduplication
      .withShardKey(Shard[TradeEvent].key)
      .some

  val swtSettings =
    PulsarProducer
      .Settings[IO, SwitchEvent]()
      .withDeduplication
      .withMessageKey(Compaction[SwitchEvent].key)
      .some


  def resources =
    for
      config <- Resource.eval(Config.load[IO])
      pulsar <- Pulsar.make[IO](config.pulsar.url, Pulsar.Settings().withTransactions)
      _ <- Resource.eval(Logger[IO].info(s"Initializing service: ${config.appId.show}"))
      server = Ember.default[IO](config.httpPort)
      cmdTopic = AppTopic.TradingCommands.make(config.pulsar)
      evtTopic = AppTopic.TradingEvents.make(config.pulsar)
      swcTopic = AppTopic.SwithCommands.make(config.pulsar)
      sweTopic = AppTopic.SwitchEvents.make(config.pulsar)
      trProducer <- Producer.pulsar[IO, TradeEvent](pulsar, evtTopic, evtSettings)
      swProducer <- Producer.pulsar[IO, SwitchEvent](pulsar, sweTopic, swtSettings)
      trConsumer <- Consumer.pulsar[IO, TradeCommand](pulsar, cmdTopic, cmdSub(config.appId))
      swConsumer <- Consumer.pulsar[IO, SwitchCommand](pulsar, swcTopic, swtSub(config.appId), compact)
      engine = Engine.fsm(trProducer, swProducer, Txn.make(pulsar), trConsumer, swConsumer)
    yield (server, trConsumer, swConsumer, engine)


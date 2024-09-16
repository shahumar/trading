pages = [{"l":"index.html#","e":false,"i":"","n":"lib","t":"lib","d":"","k":"static","x":""},
{"l":"trading/lib.html#","e":false,"i":"","n":"trading.lib","t":"trading.lib","d":"","k":"package","x":""},
{"l":"trading/lib.html#eitherUnionIso-fffff4bc","e":false,"i":"","n":"eitherUnionIso","t":"eitherUnionIso[E <: Matchable, A <: Matchable]: Iso[Either[E, A], E | A]","d":"trading.lib","k":"def","x":""},
{"l":"trading/lib/Acker.html#","e":false,"i":"","n":"Acker","t":"Acker[F[_], A]","d":"trading.lib","k":"trait","x":""},
{"l":"trading/lib/Acker.html#ack-fffffd19","e":false,"i":"","n":"ack","t":"ack(id: MsgId): F[Unit]","d":"trading.lib.Acker","k":"def","x":""},
{"l":"trading/lib/Acker.html#ack-fffff7c1","e":false,"i":"","n":"ack","t":"ack(ids: Set[MsgId]): F[Unit]","d":"trading.lib.Acker","k":"def","x":""},
{"l":"trading/lib/Acker.html#ack-e1","e":false,"i":"","n":"ack","t":"ack(id: MsgId, tx: Txn): F[Unit]","d":"trading.lib.Acker","k":"def","x":""},
{"l":"trading/lib/Acker.html#nack-fffffd19","e":false,"i":"","n":"nack","t":"nack(id: MsgId): F[Unit]","d":"trading.lib.Acker","k":"def","x":""},
{"l":"trading/lib/Compaction.html#","e":false,"i":"","n":"Compaction","t":"Compaction[A]","d":"trading.lib","k":"trait","x":""},
{"l":"trading/lib/Compaction.html#key-0","e":false,"i":"","n":"key","t":"key: A => MessageKey","d":"trading.lib.Compaction","k":"def","x":""},
{"l":"trading/lib/Compaction$.html#","e":false,"i":"","n":"Compaction","t":"Compaction","d":"trading.lib","k":"object","x":""},
{"l":"trading/lib/Compaction$.html#apply-fa7","e":false,"i":"","n":"apply","t":"apply[A : Compaction]: Compaction[A]","d":"trading.lib.Compaction","k":"def","x":""},
{"l":"trading/lib/Compaction$.html#by-377","e":false,"i":"","n":"by","t":"by(s: String): MessageKey","d":"trading.lib.Compaction","k":"def","x":""},
{"l":"trading/lib/Compaction$.html#default-b0c","e":false,"i":"","n":"default","t":"default[A]: Compaction[A]","d":"trading.lib.Compaction","k":"def","x":""},
{"l":"trading/lib/Compaction$.html#given_Compaction_Alert-0","e":false,"i":"","n":"given_Compaction_Alert","t":"given_Compaction_Alert: given_Compaction_Alert","d":"trading.lib.Compaction","k":"given","x":""},
{"l":"trading/lib/Compaction$.html#given_Compaction_PriceUpdate-0","e":false,"i":"","n":"given_Compaction_PriceUpdate","t":"given_Compaction_PriceUpdate: given_Compaction_PriceUpdate","d":"trading.lib.Compaction","k":"given","x":""},
{"l":"trading/lib/Compaction$.html#given_Compaction_SwitchCommand-0","e":false,"i":"","n":"given_Compaction_SwitchCommand","t":"given_Compaction_SwitchCommand: given_Compaction_SwitchCommand","d":"trading.lib.Compaction","k":"given","x":""},
{"l":"trading/lib/Compaction$.html#given_Compaction_SwitchEvent-0","e":false,"i":"","n":"given_Compaction_SwitchEvent","t":"given_Compaction_SwitchEvent: given_Compaction_SwitchEvent","d":"trading.lib.Compaction","k":"given","x":""},
{"l":"trading/lib/Compaction$$given_Compaction_Alert$.html#","e":false,"i":"","n":"given_Compaction_Alert","t":"given_Compaction_Alert extends Compaction[Alert]","d":"trading.lib.Compaction","k":"object","x":""},
{"l":"trading/lib/Compaction$$given_Compaction_Alert$.html#key-0","e":false,"i":"","n":"key","t":"key: Alert => MessageKey","d":"trading.lib.Compaction.given_Compaction_Alert","k":"val","x":""},
{"l":"trading/lib/Compaction$$given_Compaction_PriceUpdate$.html#","e":false,"i":"","n":"given_Compaction_PriceUpdate","t":"given_Compaction_PriceUpdate extends Compaction[PriceUpdate]","d":"trading.lib.Compaction","k":"object","x":""},
{"l":"trading/lib/Compaction$$given_Compaction_PriceUpdate$.html#key-0","e":false,"i":"","n":"key","t":"key: PriceUpdate => MessageKey","d":"trading.lib.Compaction.given_Compaction_PriceUpdate","k":"val","x":""},
{"l":"trading/lib/Compaction$$given_Compaction_SwitchCommand$.html#","e":false,"i":"","n":"given_Compaction_SwitchCommand","t":"given_Compaction_SwitchCommand extends Compaction[SwitchCommand]","d":"trading.lib.Compaction","k":"object","x":""},
{"l":"trading/lib/Compaction$$given_Compaction_SwitchCommand$.html#key-0","e":false,"i":"","n":"key","t":"key: SwitchCommand => MessageKey","d":"trading.lib.Compaction.given_Compaction_SwitchCommand","k":"val","x":""},
{"l":"trading/lib/Compaction$$given_Compaction_SwitchEvent$.html#","e":false,"i":"","n":"given_Compaction_SwitchEvent","t":"given_Compaction_SwitchEvent extends Compaction[SwitchEvent]","d":"trading.lib.Compaction","k":"object","x":""},
{"l":"trading/lib/Compaction$$given_Compaction_SwitchEvent$.html#key-0","e":false,"i":"","n":"key","t":"key: SwitchEvent => MessageKey","d":"trading.lib.Compaction.given_Compaction_SwitchEvent","k":"val","x":""},
{"l":"trading/lib/Consumer.html#","e":false,"i":"","n":"Consumer","t":"Consumer[F[_], A] extends Acker[F, A]","d":"trading.lib","k":"trait","x":""},
{"l":"trading/lib/Consumer.html#lastMsgId-0","e":false,"i":"","n":"lastMsgId","t":"lastMsgId: F[Option[MsgId]]","d":"trading.lib.Consumer","k":"def","x":""},
{"l":"trading/lib/Consumer.html#receive-0","e":false,"i":"","n":"receive","t":"receive: Stream[F, A]","d":"trading.lib.Consumer","k":"def","x":""},
{"l":"trading/lib/Consumer.html#receiveM-0","e":false,"i":"","n":"receiveM","t":"receiveM: Stream[F, Msg[A]]","d":"trading.lib.Consumer","k":"def","x":""},
{"l":"trading/lib/Consumer.html#receiveM-fffff4af","e":false,"i":"","n":"receiveM","t":"receiveM(id: MsgId): Stream[F, Msg[A]]","d":"trading.lib.Consumer","k":"def","x":""},
{"l":"trading/lib/Consumer$.html#","e":false,"i":"","n":"Consumer","t":"Consumer","d":"trading.lib","k":"object","x":""},
{"l":"trading/lib/Consumer$.html#Properties-0","e":false,"i":"","n":"Properties","t":"Properties = Map[String, String]","d":"trading.lib.Consumer","k":"type","x":""},
{"l":"trading/lib/Consumer$.html#kafka-3b","e":false,"i":"","n":"kafka","t":"kafka[F[_] : Async, A](settings: ConsumerSettings[F, String, A], topic: String): Resource[F, Consumer[F, A]]","d":"trading.lib.Consumer","k":"def","x":""},
{"l":"trading/lib/Consumer$.html#local-743","e":false,"i":"","n":"local","t":"local[F[_] : Applicative, A](queue: Queue[F, Option[A]]): Consumer[F, A]","d":"trading.lib.Consumer","k":"def","x":""},
{"l":"trading/lib/Consumer$.html#pulsar-50f","e":false,"i":"","n":"pulsar","t":"pulsar[F[_] : Logger, A : Encoder](client: T, topic: Topic, sub: Subscription, settings: Option[Settings[F, A]]): Resource[F, Consumer[F, A]]","d":"trading.lib.Consumer","k":"def","x":""},
{"l":"trading/lib/Consumer$$Msg.html#","e":false,"i":"","n":"Msg","t":"Msg[A](id: MsgId, props: Properties, payload: A)","d":"trading.lib.Consumer","k":"class","x":""},
{"l":"trading/lib/Consumer$$MsgId.html#","e":false,"i":"","n":"MsgId","t":"MsgId","d":"trading.lib.Consumer","k":"enum","x":""},
{"l":"trading/lib/Consumer$$MsgId$$Pulsar.html#","e":false,"i":"","n":"Pulsar","t":"Pulsar(id: MessageId)","d":"trading.lib.Consumer.MsgId","k":"class","x":""},
{"l":"trading/lib/Consumer$$MsgId.html#Dummy-0","e":false,"i":"","n":"Dummy","t":"Dummy extends MsgId","d":"trading.lib.Consumer.MsgId","k":"val","x":""},
{"l":"trading/lib/Consumer$$MsgId$$Pulsar.html#","e":false,"i":"","n":"Pulsar","t":"Pulsar(id: MessageId)","d":"trading.lib.Consumer.MsgId","k":"class","x":""},
{"l":"trading/lib/Consumer$$MsgId$.html#","e":false,"i":"","n":"MsgId","t":"MsgId","d":"trading.lib.Consumer","k":"object","x":""},
{"l":"trading/lib/Consumer$$MsgId$.html#earliest-0","e":false,"i":"","n":"earliest","t":"earliest: MsgId","d":"trading.lib.Consumer.MsgId","k":"def","x":""},
{"l":"trading/lib/Consumer$$MsgId$.html#from-201","e":false,"i":"","n":"from","t":"from(str: String): MsgId","d":"trading.lib.Consumer.MsgId","k":"def","x":""},
{"l":"trading/lib/Consumer$$MsgId$.html#latest-0","e":false,"i":"","n":"latest","t":"latest: MsgId","d":"trading.lib.Consumer.MsgId","k":"def","x":""},
{"l":"trading/lib/DistLock.html#","e":false,"i":"","n":"DistLock","t":"DistLock[F[_]]","d":"trading.lib","k":"trait","x":""},
{"l":"trading/lib/DistLock.html#refresh-0","e":false,"i":"","n":"refresh","t":"refresh: F[Unit]","d":"trading.lib.DistLock","k":"def","x":""},
{"l":"trading/lib/DistLock$.html#","e":false,"i":"","n":"DistLock","t":"DistLock","d":"trading.lib","k":"object","x":""},
{"l":"trading/lib/DistLock$.html#from-753","e":false,"i":"","n":"from","t":"from[F[_] : Temporal](lockName: String, appId: AppId, redis: RedisCommands[F, String, String]): Resource[F, DistLock[F]]","d":"trading.lib.DistLock","k":"def","x":""},
{"l":"trading/lib/DistLock$.html#make-fffffb33","e":false,"i":"","n":"make","t":"make[F[_] : Temporal](lockName: String, appId: AppId, client: RedisClient): Resource[F, DistLock[F]]","d":"trading.lib.DistLock","k":"def","x":""},
{"l":"trading/lib/FSM.html#","e":false,"i":"","n":"FSM","t":"FSM[F[_], S, I, O](run: (S, I) => F[(S, O)])","d":"trading.lib","k":"class","x":""},
{"l":"trading/lib/FSM.html#runS-5f0","e":false,"i":"","n":"runS","t":"runS(using F: Functor[F]): (S, I) => F[S]","d":"trading.lib.FSM","k":"def","x":""},
{"l":"trading/lib/FSM$.html#","e":false,"i":"","n":"FSM","t":"FSM","d":"trading.lib","k":"object","x":""},
{"l":"trading/lib/FSM$.html#id-8b","e":false,"i":"","n":"id","t":"id[S, I, O](run: (S, I) => Id[(S, O)]): FSM[[A] =>> A, S, I, O]","d":"trading.lib.FSM","k":"def","x":""},
{"l":"trading/lib/GenUUID.html#","e":false,"i":"","n":"GenUUID","t":"GenUUID[F[_]]","d":"trading.lib","k":"trait","x":""},
{"l":"trading/lib/GenUUID.html#make-fffff376","e":false,"i":"","n":"make","t":"make[A : IsUUID]: F[A]","d":"trading.lib.GenUUID","k":"def","x":""},
{"l":"trading/lib/GenUUID$.html#","e":false,"i":"","n":"GenUUID","t":"GenUUID","d":"trading.lib","k":"object","x":""},
{"l":"trading/lib/GenUUID$.html#apply-fffff551","e":false,"i":"","n":"apply","t":"apply[F[_] : GenUUID]: GenUUID[F]","d":"trading.lib.GenUUID","k":"def","x":""},
{"l":"trading/lib/GenUUID$.html#given_GenUUID_F-9a0","e":false,"i":"","n":"given_GenUUID_F","t":"given_GenUUID_F[F[_] : Sync]: given_GenUUID_F[F]","d":"trading.lib.GenUUID","k":"given","x":""},
{"l":"trading/lib/Logger.html#","e":false,"i":"","n":"Logger","t":"Logger[F[_]]","d":"trading.lib","k":"trait","x":""},
{"l":"trading/lib/Logger.html#debug-fffffe23","e":false,"i":"","n":"debug","t":"debug(str: => String): F[Unit]","d":"trading.lib.Logger","k":"def","x":""},
{"l":"trading/lib/Logger.html#error-fffffe23","e":false,"i":"","n":"error","t":"error(str: => String): F[Unit]","d":"trading.lib.Logger","k":"def","x":""},
{"l":"trading/lib/Logger.html#info-fffffe23","e":false,"i":"","n":"info","t":"info(str: => String): F[Unit]","d":"trading.lib.Logger","k":"def","x":""},
{"l":"trading/lib/Logger.html#warn-fffffe23","e":false,"i":"","n":"warn","t":"warn(str: => String): F[Unit]","d":"trading.lib.Logger","k":"def","x":""},
{"l":"trading/lib/Logger$.html#","e":false,"i":"","n":"Logger","t":"Logger","d":"trading.lib","k":"object","x":""},
{"l":"trading/lib/Logger$.html#apply-e7d","e":false,"i":"","n":"apply","t":"apply[F[_] : Logger]: Logger[F]","d":"trading.lib.Logger","k":"def","x":""},
{"l":"trading/lib/Logger$.html#from-fffff0f3","e":false,"i":"","n":"from","t":"from[F[_]](log: Logger[F]): Logger[F]","d":"trading.lib.Logger","k":"def","x":""},
{"l":"trading/lib/Logger$.html#given_Logger_F-235","e":false,"i":"","n":"given_Logger_F","t":"given_Logger_F[F[_] : Sync]: Logger[F]","d":"trading.lib.Logger","k":"given","x":""},
{"l":"trading/lib/Logger$.html#pulsar-d00","e":false,"i":"","n":"pulsar","t":"pulsar[F[_] : Logger, A : Encoder](flow: \"in\" | \"out\"): A => URL => F[Unit]","d":"trading.lib.Logger","k":"def","x":""},
{"l":"trading/lib/Logger$.html#redisLog-fffff85f","e":false,"i":"","n":"redisLog","t":"redisLog[F[_]](using L: Logger[F]): Log[F]","d":"trading.lib.Logger","k":"given","x":""},
{"l":"trading/lib/Logger$$NoOp$.html#","e":false,"i":"","n":"NoOp","t":"NoOp","d":"trading.lib.Logger","k":"object","x":""},
{"l":"trading/lib/Logger$$NoOp$.html#given_Logger_F-fffff4f5","e":false,"i":"","n":"given_Logger_F","t":"given_Logger_F[F[_] : Applicative]: given_Logger_F[F]","d":"trading.lib.Logger.NoOp","k":"given","x":""},
{"l":"trading/lib/Producer.html#","e":false,"i":"","n":"Producer","t":"Producer[F[_], A]","d":"trading.lib","k":"trait","x":""},
{"l":"trading/lib/Producer.html#send-d26","e":false,"i":"","n":"send","t":"send(a: A): F[Unit]","d":"trading.lib.Producer","k":"def","x":""},
{"l":"trading/lib/Producer.html#send-fffffd0e","e":false,"i":"","n":"send","t":"send(a: A, properties: Map[String, String]): F[Unit]","d":"trading.lib.Producer","k":"def","x":""},
{"l":"trading/lib/Producer.html#send-fffff534","e":false,"i":"","n":"send","t":"send(a: A, tx: Txn): F[Unit]","d":"trading.lib.Producer","k":"def","x":""},
{"l":"trading/lib/Producer.html#send-fffff84c","e":false,"i":"","n":"send","t":"send(a: A, properties: Map[String, String], tx: Txn): F[Unit]","d":"trading.lib.Producer","k":"def","x":""},
{"l":"trading/lib/Producer$.html#","e":false,"i":"","n":"Producer","t":"Producer","d":"trading.lib","k":"object","x":""},
{"l":"trading/lib/Producer$.html#dummy-b49","e":false,"i":"","n":"dummy","t":"dummy[F[_] : Applicative, A]: Producer[F, A]","d":"trading.lib.Producer","k":"def","x":""},
{"l":"trading/lib/Producer$.html#dummySeqIdMaker-fffff7e5","e":false,"i":"","n":"dummySeqIdMaker","t":"dummySeqIdMaker[F[_] : Applicative, A]: SeqIdMaker[F, A]","d":"trading.lib.Producer","k":"def","x":""},
{"l":"trading/lib/Producer$.html#kafka-b1f","e":false,"i":"","n":"kafka","t":"kafka[F[_] : Async, A](settings: ProducerSettings[F, String, A], topic: String): Resource[F, Producer[F, A]]","d":"trading.lib.Producer","k":"def","x":""},
{"l":"trading/lib/Producer$.html#local-fffffbfc","e":false,"i":"","n":"local","t":"local[F[_] : Applicative, A](queue: Queue[F, Option[A]]): Resource[F, Producer[F, A]]","d":"trading.lib.Producer","k":"def","x":""},
{"l":"trading/lib/Producer$.html#pulsar-fffffa16","e":false,"i":"","n":"pulsar","t":"pulsar[F[_] : Parallel, A : Encoder](client: T, topic: Single, settings: Option[Settings[F, A]]): Resource[F, Producer[F, A]]","d":"trading.lib.Producer","k":"def","x":""},
{"l":"trading/lib/Producer$.html#test-fffffa37","e":false,"i":"","n":"test","t":"test[F[_] : Applicative, A](ref: Ref[F, Option[A]]): Producer[F, A]","d":"trading.lib.Producer","k":"def","x":""},
{"l":"trading/lib/Producer$.html#testMany-fffffa37","e":false,"i":"","n":"testMany","t":"testMany[F[_] : Applicative, A](ref: Ref[F, List[A]]): Producer[F, A]","d":"trading.lib.Producer","k":"def","x":""},
{"l":"trading/lib/Producer$$Dummy.html#","e":false,"i":"","n":"Dummy","t":"Dummy[F[_], A] extends Producer[F, A]","d":"trading.lib.Producer","k":"class","x":""},
{"l":"trading/lib/Producer$$Dummy.html#send-d26","e":false,"i":"","n":"send","t":"send(a: A): F[Unit]","d":"trading.lib.Producer.Dummy","k":"def","x":""},
{"l":"trading/lib/Producer$$Dummy.html#send-fffffd0e","e":false,"i":"","n":"send","t":"send(a: A, properties: Map[String, String]): F[Unit]","d":"trading.lib.Producer.Dummy","k":"def","x":""},
{"l":"trading/lib/Producer$$Dummy.html#send-fffff534","e":false,"i":"","n":"send","t":"send(a: A, tx: Txn): F[Unit]","d":"trading.lib.Producer.Dummy","k":"def","x":""},
{"l":"trading/lib/Producer$$Dummy.html#send-fffff84c","e":false,"i":"","n":"send","t":"send(a: A, properties: Map[String, String], tx: Txn): F[Unit]","d":"trading.lib.Producer.Dummy","k":"def","x":""},
{"l":"trading/lib/Shard.html#","e":false,"i":"","n":"Shard","t":"Shard[A]","d":"trading.lib","k":"trait","x":""},
{"l":"trading/lib/Shard.html#key-0","e":false,"i":"","n":"key","t":"key: A => ShardKey","d":"trading.lib.Shard","k":"def","x":""},
{"l":"trading/lib/Shard$.html#","e":false,"i":"","n":"Shard","t":"Shard","d":"trading.lib","k":"object","x":""},
{"l":"trading/lib/Shard$.html#apply-4f1","e":false,"i":"","n":"apply","t":"apply[A : Shard]: Shard[A]","d":"trading.lib.Shard","k":"def","x":""},
{"l":"trading/lib/Shard$.html#by-c00","e":false,"i":"","n":"by","t":"by(s: String): ShardKey","d":"trading.lib.Shard","k":"def","x":""},
{"l":"trading/lib/Shard$.html#default-fffff277","e":false,"i":"","n":"default","t":"default[A]: Shard[A]","d":"trading.lib.Shard","k":"def","x":""},
{"l":"trading/lib/Shard$.html#given_Shard_Alert-0","e":false,"i":"","n":"given_Shard_Alert","t":"given_Shard_Alert: given_Shard_Alert","d":"trading.lib.Shard","k":"given","x":""},
{"l":"trading/lib/Shard$.html#given_Shard_ForecastCommand-0","e":false,"i":"","n":"given_Shard_ForecastCommand","t":"given_Shard_ForecastCommand: given_Shard_ForecastCommand","d":"trading.lib.Shard","k":"given","x":""},
{"l":"trading/lib/Shard$.html#given_Shard_String-0","e":false,"i":"","n":"given_Shard_String","t":"given_Shard_String: given_Shard_String","d":"trading.lib.Shard","k":"given","x":""},
{"l":"trading/lib/Shard$.html#given_Shard_TradeCommand-0","e":false,"i":"","n":"given_Shard_TradeCommand","t":"given_Shard_TradeCommand: given_Shard_TradeCommand","d":"trading.lib.Shard","k":"given","x":""},
{"l":"trading/lib/Shard$.html#given_Shard_TradeEvent-0","e":false,"i":"","n":"given_Shard_TradeEvent","t":"given_Shard_TradeEvent: given_Shard_TradeEvent","d":"trading.lib.Shard","k":"given","x":""},
{"l":"trading/lib/Shard$$given_Shard_Alert$.html#","e":false,"i":"","n":"given_Shard_Alert","t":"given_Shard_Alert extends Shard[Alert]","d":"trading.lib.Shard","k":"object","x":""},
{"l":"trading/lib/Shard$$given_Shard_Alert$.html#key-0","e":false,"i":"","n":"key","t":"key: Alert => ShardKey","d":"trading.lib.Shard.given_Shard_Alert","k":"val","x":""},
{"l":"trading/lib/Shard$$given_Shard_ForecastCommand$.html#","e":false,"i":"","n":"given_Shard_ForecastCommand","t":"given_Shard_ForecastCommand extends Shard[ForecastCommand]","d":"trading.lib.Shard","k":"object","x":""},
{"l":"trading/lib/Shard$$given_Shard_ForecastCommand$.html#key-0","e":false,"i":"","n":"key","t":"key: ForecastCommand => ShardKey","d":"trading.lib.Shard.given_Shard_ForecastCommand","k":"val","x":""},
{"l":"trading/lib/Shard$$given_Shard_String$.html#","e":false,"i":"","n":"given_Shard_String","t":"given_Shard_String extends Shard[String]","d":"trading.lib.Shard","k":"object","x":""},
{"l":"trading/lib/Shard$$given_Shard_String$.html#key-0","e":false,"i":"","n":"key","t":"key: String => ShardKey","d":"trading.lib.Shard.given_Shard_String","k":"val","x":""},
{"l":"trading/lib/Shard$$given_Shard_TradeCommand$.html#","e":false,"i":"","n":"given_Shard_TradeCommand","t":"given_Shard_TradeCommand extends Shard[TradeCommand]","d":"trading.lib.Shard","k":"object","x":""},
{"l":"trading/lib/Shard$$given_Shard_TradeCommand$.html#key-0","e":false,"i":"","n":"key","t":"key: TradeCommand => ShardKey","d":"trading.lib.Shard.given_Shard_TradeCommand","k":"val","x":""},
{"l":"trading/lib/Shard$$given_Shard_TradeEvent$.html#","e":false,"i":"","n":"given_Shard_TradeEvent","t":"given_Shard_TradeEvent extends Shard[TradeEvent]","d":"trading.lib.Shard","k":"object","x":""},
{"l":"trading/lib/Shard$$given_Shard_TradeEvent$.html#key-0","e":false,"i":"","n":"key","t":"key: TradeEvent => ShardKey","d":"trading.lib.Shard.given_Shard_TradeEvent","k":"val","x":""},
{"l":"trading/lib/Time.html#","e":false,"i":"","n":"Time","t":"Time[F[_]]","d":"trading.lib","k":"trait","x":""},
{"l":"trading/lib/Time.html#timestamp-0","e":false,"i":"","n":"timestamp","t":"timestamp: F[Timestamp]","d":"trading.lib.Time","k":"def","x":""},
{"l":"trading/lib/Time$.html#","e":false,"i":"","n":"Time","t":"Time","d":"trading.lib","k":"object","x":""},
{"l":"trading/lib/Time$.html#apply-bf7","e":false,"i":"","n":"apply","t":"apply[F[_] : Time]: Time[F]","d":"trading.lib.Time","k":"def","x":""},
{"l":"trading/lib/Time$.html#given_Time_F-fffff532","e":false,"i":"","n":"given_Time_F","t":"given_Time_F[F[_] : Sync]: given_Time_F[F]","d":"trading.lib.Time","k":"given","x":""},
{"l":"trading/lib/Txn.html#","e":false,"i":"","n":"Txn","t":"Txn","d":"trading.lib","k":"trait","x":""},
{"l":"trading/lib/Txn.html#get-0","e":false,"i":"","n":"get","t":"get: Tx","d":"trading.lib.Txn","k":"def","x":""},
{"l":"trading/lib/Txn$.html#","e":false,"i":"","n":"Txn","t":"Txn","d":"trading.lib","k":"object","x":""},
{"l":"trading/lib/Txn$.html#dummy-0","e":false,"i":"","n":"dummy","t":"dummy: Resource[IO, Txn]","d":"trading.lib.Txn","k":"def","x":""},
{"l":"trading/lib/Txn$.html#make-fffff50f","e":false,"i":"","n":"make","t":"make(pulsar: T): Resource[IO, Txn]","d":"trading.lib.Txn","k":"def","x":""}];
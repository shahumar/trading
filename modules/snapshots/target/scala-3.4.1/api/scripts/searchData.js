pages = [{"l":"index.html#","e":false,"i":"","n":"snapshots","t":"snapshots","d":"","k":"static","x":""},
{"l":"trading/snapshots.html#","e":false,"i":"","n":"trading.snapshots","t":"trading.snapshots","d":"","k":"package","x":""},
{"l":"trading/snapshots.html#Tick-0","e":false,"i":"","n":"Tick","t":"Tick = Unit","d":"trading.snapshots","k":"type","x":""},
{"l":"trading/snapshots/Config$.html#","e":false,"i":"","n":"Config","t":"Config","d":"trading.snapshots","k":"object","x":""},
{"l":"trading/snapshots/Config$.html#load-55b","e":false,"i":"","n":"load","t":"load[F[_] : Async]: F[SnapshotsConfig]","d":"trading.snapshots.Config","k":"def","x":""},
{"l":"trading/snapshots/Engine$.html#","e":false,"i":"","n":"Engine","t":"Engine","d":"trading.snapshots","k":"object","x":""},
{"l":"trading/snapshots/Engine$.html#In-0","e":false,"i":"","n":"In","t":"In = Either[Msg[TradeEvent], Msg[SwitchEvent]] | Tick","d":"trading.snapshots.Engine","k":"type","x":""},
{"l":"trading/snapshots/Engine$.html#fsm-fffff481","e":false,"i":"","n":"fsm","t":"fsm[F[_] : Logger](tradeAcker: Acker[F, TradeEvent], switchAcker: Acker[F, SwitchEvent], writer: SnapshotWriter[F]): FSM[F, (TradeState, List[MsgId]), In, Unit]","d":"trading.snapshots.Engine","k":"def","x":""},
{"l":"trading/snapshots/Main$.html#","e":false,"i":"","n":"Main","t":"Main extends Simple","d":"trading.snapshots","k":"object","x":""},
{"l":"trading/snapshots/Main$.html#compact-0","e":false,"i":"","n":"compact","t":"compact: Option[Settings[IO, SwitchEvent]]","d":"trading.snapshots.Main","k":"val","x":""},
{"l":"trading/snapshots/Main$.html#mkSub-fffff016","e":false,"i":"","n":"mkSub","t":"mkSub(appId: AppId): Subscription","d":"trading.snapshots.Main","k":"def","x":""},
{"l":"trading/snapshots/Main$.html#resources-0","e":false,"i":"","n":"resources","t":"resources: Resource[IO, (Resource[IO, Server], Resource[IO, DistLock[IO]], Consumer[IO, TradeEvent], Consumer[IO, SwitchEvent], SnapshotReader[IO], FSM[IO, (TradeState, List[MsgId]), In, Unit])]","d":"trading.snapshots.Main","k":"def","x":""},
{"l":"trading/snapshots/Main$.html#run-0","e":false,"i":"","n":"run","t":"run: IO[Unit]","d":"trading.snapshots.Main","k":"def","x":""},
{"l":"trading/snapshots/SnapshotsConfig.html#","e":false,"i":"","n":"SnapshotsConfig","t":"SnapshotsConfig(httpPort: Port, pulsar: Config, redisUri: RedisURI, keyExpiration: KeyExpiration, appId: AppId)","d":"trading.snapshots","k":"class","x":""}];
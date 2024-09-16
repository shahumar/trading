package retails.catalogue.store


import cats.~>
import cats.arrow.FunctionK
import cats.effect.kernel.{ Resource, Async }
import doobie.free.connection.setAutoCommit
import cats.effect.kernel.Resource.ExitCase.*
import cats.syntax.all.*
import doobie.{ConnectionIO, Transactor, WeakAsync}
import trading.lib.Logger
import doobie.hi.connection.{ commit, rollback }

trait DoobieTx[F[_]]:
  def transaction(xa: Transactor[F]): Resource[F, ConnectionIO ~> F]
  
  
object DoobieTx:
  def apply[F[_]: DoobieTx]: DoobieTx[F] = summon
  
  given [F[_]: Async: Logger]: DoobieTx[F] with
    override def transaction(xa: Transactor[F]): Resource[F, ConnectionIO ~> F] =
      WeakAsync.liftK[F, ConnectionIO].flatMap { fk =>
        xa.connect(xa.kernel).flatMap { conn =>
          def log(s: => String) = fk(Logger[F].debug(s"DB: $s"))
          
          val rawTrans = FunctionK.lift[ConnectionIO, F] {
            [T] => (fa: ConnectionIO[T]) => fa.foldMap(xa.interpret).run(conn)
          }
          
          Resource
            .makeCase(setAutoCommit(false)) {
              case (_, Succeeded) => log("COMMIT") *> commit
              case (_, Canceled | Errored(_)) => log("ROLLBACK") *> rollback
            }
            .mapK(rawTrans)
            .as(rawTrans)
        }
      }
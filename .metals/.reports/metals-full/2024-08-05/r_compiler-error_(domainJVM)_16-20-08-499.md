file://<WORKSPACE>/modules/domain/shared/src/main/scala/trading/domain.scala
### scala.MatchError: TypeDef(RedisURI,Select(Ident(RedisURI),Type)) (of class dotty.tools.dotc.ast.Trees$TypeDef)

occurred in the presentation compiler.

presentation compiler configuration:


action parameters:
offset: 331
uri: file://<WORKSPACE>/modules/domain/shared/src/main/scala/trading/domain.scala
text:
```scala
package trading.domain

import java.time.Instant

import scala.concurrent.duration.FiniteDuration

import trading.* 

import cats.{Eq, Monoid, Order, Show}

import io.circe.*

export Extensions.*
import OrphanIntances.given

type PulsarURI = PulsarURI.Type 
object PulsarURI extends Newtype[String]

type RedisURI = RedisURI.Type
o@@



```



#### Error stacktrace:

```
dotty.tools.pc.completions.KeywordsCompletions$.checkTemplateForNewParents$$anonfun$2(KeywordsCompletions.scala:219)
	scala.Option.map(Option.scala:242)
	dotty.tools.pc.completions.KeywordsCompletions$.checkTemplateForNewParents(KeywordsCompletions.scala:220)
	dotty.tools.pc.completions.KeywordsCompletions$.contribute(KeywordsCompletions.scala:45)
	dotty.tools.pc.completions.Completions.completions(Completions.scala:115)
	dotty.tools.pc.completions.CompletionProvider.completions(CompletionProvider.scala:92)
	dotty.tools.pc.ScalaPresentationCompiler.complete$$anonfun$1(ScalaPresentationCompiler.scala:143)
```
#### Short summary: 

scala.MatchError: TypeDef(RedisURI,Select(Ident(RedisURI),Type)) (of class dotty.tools.dotc.ast.Trees$TypeDef)
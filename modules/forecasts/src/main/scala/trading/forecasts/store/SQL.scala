package trading.forecasts.store

import java.util.UUID
import trading.domain.*
import trading.events.*
import cats.syntax.show.*
import doobie.*
import doobie.implicits.*
import doobie.implicits.javatimedrivernative.*
import io.circe.syntax.*
import io.github.iltotore.iron.*
import trading.domain.VoteResult.{Down, Up}

object SQL:
  
  given Meta[UUID] = Meta[String].imap[UUID](UUID.fromString)(_.toString)
  
  given Read[Author] = Read[(UUID, String, Option[String], Option[UUID])].map { (id, name, website, fid) =>
    Author(AuthorId(id), AuthorName(name), website.map(Website(_)), fid.toSet.map(ForecastId(_)))
  }
  
  given Read[Forecast] = Read[(UUID, String, String, String, Int)].map{(id, sl, tag, desc, sc) =>
    Forecast(ForecastId(id), Symbol(sl.refine), ForecastTag.from(tag), ForecastDescription(desc), ForecastScore(sc))
  }
  
  extension (res: VoteResult)
    def asInt: Int = res match
      case Up => 1
      case Down => -1
      
  val selectAuthor: AuthorId => Query0[Author] = id =>
    sql"""
      SELECT a.id, a.name, a.website, f.id FROM authors AS a 
      LEFT JOIN author_forecasts AS f On a.id=f.author_id 
      WHERE a.id=${id.show}
       """.query[Author]
    
  val insertAuthor: Author => Update0 = a =>
    sql"""
      INSERT INTO authors (id, name, website)
      VALUES (${a.id.value}, ${a.website.map(_.value)})
       """.update

  def insertAuthorForecasts(a: Author): ConnectionIO[Int] =
    val sql = "INSERT INTO author_forecasts (id, author_id) VALUES (?, ?)"
    val ids = a.forecasts.toList.map(_.value -> a.id.value)
    Update[(UUID, UUID)](sql).updateMany(ids)
    
  def updateAuthorForecast(id: AuthorId, fid: ForecastId): Update0 =
    sql"""
         INSERT INTO author_forecasts (id, author_id)
         VALUES (${fid.value}, ${id.value})
       """.update
    
  val selectForecast: ForecastId => Query0[Forecast] = id =>
    sql"""
      SELECT id, symbol, tag, description, score FROM forecasts
      WHERE id=${id.show}
       """.query[Forecast]
    
  val insertForecast: Forecast => Update0 = f =>
    sql"""
      INSERT INTO forecasts (id, symbol, tag, description, score)
      VALUES (${f.id.value}, ${f.symbol.show}, ${f.tag.show}, ${f.description.show}, ${f.score.value})
       """.update
    
  def updateVote(id: ForecastId, res: VoteResult): Update0 =
    sql"""
         UPDATE forecasts
         SET score=COALESCE(score, 0)+${res.asInt}
         WHERE id=${id.show}
       """.update
    
  val insertOutbox: Either[AuthorEvent, ForecastEvent] => Update0 = e =>
    sql"""
      insert into outbox (event_id, correlation_id, event, created_at)
      values (
        ${e.fold(_.id, _.id).value},
        ${e.fold(_.cid, _.cid).value},
        ${e.fold(_.asJson, _.asJson).noSpaces},
        ${e.fold(_.createdAt, _.createdAt).value}
      )
       """.update
    
  val insertVote: ForecastEvent.Voted => Update0 = e =>
    sql"""
      insert into votes (event_id, fid, result, created_at)
      values (${e.id.value}, ${e.forecastId.value}, ${e.result.asInt}, ${e.createdAt.value})
       """.update
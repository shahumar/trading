package retails.catalogue.services

import cats.Applicative
import cats.syntax.all.*
import cats.effect.kernel.Concurrent
import fs2.io.file.{Files, Path}
import org.http4s.multipart.Part

trait ImageStore[F[_]]:
  def save(path: String, bytes: Option[Part[F]]): F[Option[Path]]
  def createDir(path: Option[Path]): F[Unit]
  def fileExists(path: Option[Path]): F[Boolean]


object ImageStore:
  val basePath: Path = Path("images")

  def from[F[_]: Files: Applicative: Concurrent] = new ImageStore[F]:

    def save(path: String, image: Option[Part[F]]): F[Option[Path]] =
      val filePath = basePath / Path(path)
      val makePath = fileExists(filePath.parent) match {
          case false => createDir(filePath.parent)
          case _ => Applicative[F].unit
      }
      makePath >>
        image.traverse { part =>
          part
            .body
            .through(Files[F].writeAll(filePath))
            .compile
            .drain
            .as(filePath)
        }

    def createDir(path: Option[Path]): F[Unit] = path match
      case Some(url) => Files[F].createDirectories(url)
      case None => Applicative[F].unit

    def fileExists(path: Option[Path]): F[Boolean] = path match
      case Some(url) =>  Files[F].exists(url)
      case None => Applicative[F].pure(false)


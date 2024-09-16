package auth.domain


import cats.{Eq, Show}

import javax.crypto.Cipher
import scala.util.control.NoStackTrace
import io.circe.*

type UserId = UserId.Type
object UserId extends IdNewtype

case class Username(value: String) extends Newtype[String]
case class Password(value: String) extends Newtype[String]
case class EncryptedPassword(value: String) extends Newtype[String]
case class EncryptCipher(value: Cipher) extends NewTypeWithoutEq[Cipher]
case class DecryptCipher(value: Cipher) extends NewTypeWithoutEq[Cipher]

type ClaimContent = ClaimContent.Type
object ClaimContent extends IdNewtype:
  def apply(value: String): ClaimContent = value.asInstanceOf[ClaimContent]

  given jsonDecoder: Decoder[ClaimContent] =
    Decoder.forProduct1("uuid")(ClaimContent.apply)

type UsernameParam = UsernameParam.Type
object UsernameParam extends Newtype[String]:
  def toDomain: Username = Username(value.toString().toLowerCase)

type PasswordParam = PasswordParam.Type
object PasswordParam extends Newtype[String]:
  def toDomain: Password = Password(value.toString)

type PasswordSalt = PasswordSalt.Type
object PasswordSalt extends Newtype[String]:
  def secret: String = value.toString

case class UserNotFound(username: Username) extends NoStackTrace
case class UsernameInUse(username: Username) extends NoStackTrace
case class InvalidPassword(username: Username) extends NoStackTrace

case object TokenNotFound extends NoStackTrace


















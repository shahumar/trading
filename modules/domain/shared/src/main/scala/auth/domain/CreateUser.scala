package auth.domain

import cats.Show
import cats.derived.*
import io.circe.Codec

final case class CreateUser(
                             username: String,
                             password: String
                           ) derives Codec.AsObject, Show

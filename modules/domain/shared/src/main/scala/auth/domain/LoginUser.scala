package auth.domain

import cats.Show
import cats.derived.*
import io.circe.Codec

final case class LoginUser(
                      username: UsernameParam,
                      password: PasswordParam
                    ) derives Codec.AsObject, Show


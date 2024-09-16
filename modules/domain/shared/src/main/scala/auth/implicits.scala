package auth.domain

import cats.kernel.Eq
import dev.profunktor.auth.jwt.JwtToken



object JwtImplicits:
  given jwtTokenEq: Eq[JwtToken] = Eq.instance[JwtToken]((t1, t2) => t1.value == t2.value)


export JwtImplicits.given
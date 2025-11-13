package framework.security

import com.auth0.jwt.JWT
import com.auth0.jwt.algorithms.Algorithm
import com.auth0.jwt.exceptions.JWTVerificationException
import com.auth0.jwt.interfaces.DecodedJWT
import framework.config.ConfigProperty.*
import framework.exception.UnauthorizedException
import java.time.Instant
import java.time.temporal.ChronoUnit

private const val EMPTY_JWT = "JWT 토큰이 비어 있습니다."
private const val ILLEGAL_JWT = "부적절한 JWT 토큰입니다."
private const val ACCESS_DENIED = "접근 권한이 없습니다."

private val algorithm = Algorithm.HMAC256(JwtSecret.value)
private const val ROLES_CLAIM_NAME = "roles"

fun createToken(
    key: Any,
    roles: List<Role>
): String {
    return JWT.create()
        .withIssuer(JwtIssuer.value)
        .withSubject(key.toString())
        .withClaim(ROLES_CLAIM_NAME, roles.map { it.name })
        .withExpiresAt(Instant.now().plus(JwtExpiredMs.value, ChronoUnit.MILLIS))
        .sign(algorithm)
}

fun validateToken(token: String?, requiredRole: Role) {
    if (token.isNullOrBlank()) {
        throw UnauthorizedException(message = EMPTY_JWT)
    }

    val decodedToken = decodeToken(token)

    if (!decodedToken.containsRole(requiredRole)) {
        throw UnauthorizedException(message = ACCESS_DENIED)
    }
}

fun extractKey(token: String?): String {
    if (token.isNullOrBlank()) {
        throw UnauthorizedException(message = EMPTY_JWT)
    }

    val decodeToken = decodeToken(token)

    return decodeToken.subject
}

private fun decodeToken(token: String): DecodedJWT {
    try {
        val verifier = JWT.require(algorithm)
            .withIssuer(JwtIssuer.value)
            .build()

        return verifier.verify(token)
    } catch (exception: JWTVerificationException) {
        throw UnauthorizedException(message = ILLEGAL_JWT)
    }
}

private fun DecodedJWT.containsRole(role: Role): Boolean {
    val roles = getClaim(ROLES_CLAIM_NAME).asList(String::class.java)

    return roles.contains(role.name)
}

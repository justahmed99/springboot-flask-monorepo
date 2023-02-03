package com.efishery.fetchapp.adapter.input.rest.utils

import com.auth0.jwt.JWT
import com.auth0.jwt.algorithms.Algorithm
import com.auth0.jwt.exceptions.JWTVerificationException
import com.auth0.jwt.interfaces.DecodedJWT
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Component
import org.springframework.web.reactive.function.server.ServerRequest
import reactor.core.publisher.Mono

@Component
class JwtUtils {
    @Value("\${jwt.secret}")
    private var secret: String = ""

    fun verifyJwt(serverRequest: ServerRequest): Mono<Boolean> {
        val jwtString = serverRequest.headers().header("Authorization").joinToString()
            .replace("Bearer", "")
            .replace(" ", "")
        return Mono.just(verify(jwtString))
    }

    private fun verify(jwtString: String): Boolean {
        return try {
            val algorithm = Algorithm.HMAC256(secret)
            val verifier = JWT.require(algorithm)
                .build()
            verifier.verify(jwtString)
            true
        } catch (e: JWTVerificationException) {
            false
        }
    }

    private fun role(serverRequest: ServerRequest): String {
        val jwt = serverRequest.headers().header("Authorization").joinToString()
        val decodedJWT: DecodedJWT = JWT.decode(jwt)
        return decodedJWT.getClaim("role").toString()
    }
}
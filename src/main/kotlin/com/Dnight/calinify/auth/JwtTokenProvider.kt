package com.dnight.calinify.auth

import com.dnight.calinify.config.basicResponse.ResponseCode
import com.dnight.calinify.config.exception.ClientException
import io.jsonwebtoken.Claims
import io.jsonwebtoken.Jwts
import io.jsonwebtoken.SignatureAlgorithm
import io.jsonwebtoken.io.Decoders
import io.jsonwebtoken.security.Keys
import org.springframework.beans.factory.annotation.Value
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.Authentication
import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.core.userdetails.User
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.stereotype.Component
import java.util.*

private const val EXPIRATION_MILLISECONDS: Long = 1000 * 60 * 60 * 12

@Component
class JwtTokenProvider {
    @Value("\${jwt.secret}")
    lateinit var secretKey: String

    private val key by lazy {
        Keys.hmacShaKeyFor(Decoders.BASE64.decode(secretKey))
    }

    private fun getClaims(token: String) : Claims {
        return Jwts.parserBuilder()
            .setSigningKey(this.key)
            .build()
            .parseClaimsJwt(token)
            .body
    }

    // Token 생성
    fun createToken(authentication: Authentication): TokenInfo {
        val authorities = authentication
            .authorities
            .joinToString(separator = ",", transform = GrantedAuthority::getAuthority)

        val now = Date()
        val accessExpiration = Date(now.time + EXPIRATION_MILLISECONDS)

        val accessToken = Jwts.builder()
            .setSubject(authentication.name)
            .claim("auth", authorities)
            .setIssuedAt(now)
            .setExpiration(accessExpiration)
            .signWith(key, SignatureAlgorithm.HS256)
            .compact()

        return TokenInfo("Bearer", accessToken)
    }

    // token에서 정보 추출
    fun getAuthentication(token: String): Authentication {
        val claims : Claims = getClaims(token)
        val auth = claims["auth"]
            ?: throw ClientException(ResponseCode.Unauthorized, "unauthorized token")
        val authorities : Collection<GrantedAuthority> = (auth as String)
            .split(",")
            .map { SimpleGrantedAuthority(it) }
        val principal : UserDetails = User(claims.subject, "", authorities)

        return UsernamePasswordAuthenticationToken(principal, "", authorities)
        }

    // token 검증
    fun validateToken(token: String) : Boolean {
        try {
            getClaims(token)
            return true
        } catch (e : Exception) {
            throw ClientException(ResponseCode.Unauthorized, "unauthorized token")
        }
    }
}
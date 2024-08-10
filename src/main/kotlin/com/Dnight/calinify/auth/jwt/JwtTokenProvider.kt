package com.dnight.calinify.auth.jwt

import com.dnight.calinify.auth.dto.response.TokenResponseDTO
import com.dnight.calinify.auth.entity.CustomUserEntity
import com.dnight.calinify.config.basicResponse.ResponseCode
import com.dnight.calinify.config.exception.ClientException
import io.jsonwebtoken.Claims
import io.jsonwebtoken.Jwts
import io.jsonwebtoken.SignatureAlgorithm
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
private const val REFRESH_EXPIRATION_MILLISECONDS: Long = 1000 * 60 * 60 * 24

@Component
class JwtTokenProvider {
    /**
     * JwtAuthenticationFilter 또는 AuthService에 주입하여 사용한다.
     *
     * jwt secret key는 환경 변수로 관리하며, base64로 디코딩하여 key로 만들어 사용한다.
     *
     * @author 정인모
     */
    @Value("\${jwt.secret}")
    lateinit var secretKey: String

    private val key by lazy {
        Keys.hmacShaKeyFor(Base64.getDecoder().decode(secretKey))
    }

    /**
     * 토큰을 생성하여 반환한다.
     *
     * 로그인 단계에서만 쓰이며, refreshToken 재발급을 위해 쓰일 수도 있다.
     *
     * @see com.dnight.calinify.auth.service.AuthService.login
     */
    fun createToken(authentication: Authentication): TokenResponseDTO {
        // subject(sub)에 주입받은 userId를 추출한다.
        val userId = (authentication.principal as CustomUserEntity).userId.toString()

        // 권한 추출
        val authorities = authentication
            .authorities
            .joinToString(separator = ",", transform = GrantedAuthority::getAuthority)

        val now = Date()
        val accessExpiration = Date(now.time + EXPIRATION_MILLISECONDS)
        val refreshExpiration = Date(now.time + REFRESH_EXPIRATION_MILLISECONDS)

        val accessToken = Jwts.builder()
            .setSubject(userId)
            .claim("auth", authorities)
            .setIssuedAt(now)
            .setExpiration(accessExpiration)
            .signWith(key, SignatureAlgorithm.HS256)
            .compact()

        val refreshToken = Jwts.builder()
            .setSubject(userId)
            .claim("auth", authorities)
            .setIssuedAt(now)
            .setExpiration(refreshExpiration)
            .signWith(key, SignatureAlgorithm.HS256)
            .compact()

        return TokenResponseDTO("Bearer", accessToken, refreshToken)
    }

    private fun getClaims(token: String) : Claims {
        println(token)
        return Jwts.parserBuilder()
            .setSigningKey(key)
            .build()
            .parseClaimsJws(token)
            .body
    }

    /**
     * 토큰에서 정보를 추출한다.
     *
     * claim : token을 파싱했을 때 기본적으로 포함되는 부분을 인자로써 접근할 수 있으며,
     * 추가로 주입한 부분에 대해서는 claims[["auth"]]와 같이 키 검색 방법으로 접근할 수 있다.
     *
     * principal : UserDetail, User, CustomUserEntity 등, UserDetail을 상속받은 객체가 할당된다.
     *
     * @return [UsernamePasswordAuthenticationToken] - [Authentication]을 상속하며,
     * 해당 값은 SecurityContextHolder에 주입되며 jwt 필터의 다음 값인 userpassword 필터로 넘어간다.
     */
    fun getAuthentication(token: String): Authentication {
        val claims : Claims = getClaims(token)

        val auth = claims["auth"]
            ?: throw ClientException(ResponseCode.Unauthorized, "failed to extract user authentication")
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
            return false
        }
    }
}
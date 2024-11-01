package com.dnight.calinify.auth.jwt

import jakarta.servlet.FilterChain
import jakarta.servlet.ServletRequest
import jakarta.servlet.ServletResponse
import jakarta.servlet.http.HttpServletRequest
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.util.StringUtils
import org.springframework.web.filter.GenericFilterBean

class JwtAuthenticationFilter(
    private val jwtTokenProvider: JwtTokenProvider
) : GenericFilterBean() {
    /**
     * UsernamepasswordAuthenticationFilter 이전에 실행되는 JWT 검증 필터
     *
     * 주입받은 jwtTokenProvider 내 함수들을 활용해 토큰을 검증하고,
     *
     * 유저 관련 정보(authentication)를 추출해 [SecurityContextHolder]에 할당한다.
     *
     * contextHolder에 해당 데이터가 없을 경우, 403에러를 반환한다.
     *
     * @author 정인모
     */
    override fun doFilter(request: ServletRequest?,
                          response: ServletResponse?,
                          chain: FilterChain?) {
        val token = resolvedToken(request as HttpServletRequest)
        if (token != null && jwtTokenProvider.validateToken(token)) {
            val authentication = jwtTokenProvider.getAuthentication(token)
            SecurityContextHolder.getContext().authentication = authentication
        }
        chain?.doFilter(request, response)
    }

    private fun resolvedToken(request: HttpServletRequest): String? {
        val bearerToken = request.getHeader("Authorization")

        return if (StringUtils.hasText(bearerToken) &&
            bearerToken.startsWith("Bearer")) {
            bearerToken.substring(7)
        } else {
            null
        }
    }
}
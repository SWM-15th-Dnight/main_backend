package com.dnight.calinify.auth.auth_exception

import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.springframework.security.core.AuthenticationException
import org.springframework.security.web.AuthenticationEntryPoint
import org.springframework.stereotype.Component

@Component
class CustomAuthenticationEntryPoint : AuthenticationEntryPoint {
    /**
     * 토큰 인증이 필요한 리소스에 토큰 없이 접근한 경우, Global Exception Handler 단에 도달하기 전에 에러를 반환함
     *
     * @author 정인모
     */
    @Throws(AuthenticationException::class)
    override fun commence(request: HttpServletRequest, response: HttpServletResponse, authException: AuthenticationException) {
        response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "비로그인 유저")
    }
}
package com.dnight.calinify.auth.security

import com.dnight.calinify.auth.auth_exception.CustomAuthenticationEntryPoint
import com.dnight.calinify.auth.jwt.JwtAuthenticationFilter
import com.dnight.calinify.auth.jwt.JwtTokenProvider
import io.swagger.v3.oas.models.security.SecurityRequirement
import io.swagger.v3.oas.models.security.SecurityScheme
import org.springdoc.core.models.GroupedOpenApi
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.config.annotation.web.invoke
import org.springframework.security.config.http.SessionCreationPolicy
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.security.web.SecurityFilterChain
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter

@Configuration
@EnableWebSecurity
class SecurityConfig(
    private val jwtTokenProvider: JwtTokenProvider
) {
    @Autowired
    private lateinit var customAuthenticationEntryPoint : CustomAuthenticationEntryPoint
    /**
     * Security Filter Chain
     *
     * 보안과 관련된 총체적인 설정을 관리한다.
     *
     * @param authorizeRequests 내에 엔드 포인트별 보안, 권한 설정을 추가할 수 있다.
     * @param addFilterBefore 필터를 통해 요청이 controller에 도달하기 전, 데이터의 전처리, 인증 인가 작업을 할 수 있다.
     */
    @Bean
    fun filterChain(http: HttpSecurity): SecurityFilterChain {
        http {
            csrf { disable() }
            authorizeRequests {
                authorize("/error/**", permitAll)
                authorize("/swagger-ui/**", permitAll)
                authorize("/v3/api-docs/**", permitAll)
                authorize("/api/v1/auth/**", permitAll)
                authorize(anyRequest, authenticated)
            }
            sessionManagement {
                sessionCreationPolicy = SessionCreationPolicy.STATELESS
            }
            addFilterBefore<UsernamePasswordAuthenticationFilter>(JwtAuthenticationFilter(jwtTokenProvider))
            /**
             * authenticationEntryPoint : 토큰이 아예 없는, 비로그인 사용자가 보호된 리소스에 접근할 때 발생하는 에러
             */
            exceptionHandling {
                authenticationEntryPoint = customAuthenticationEntryPoint
            }
        }
        return http.build()
    }

    /**
     * Swagger ui 보안 설정
     *
     * 토큰을 기반으로 인증하며, Auth/Login을 통해 접근 시, 반환되는 토큰을 활용해 보호된 url에 접근할 수 있다.
     */
    @Bean
    fun springDocConfig(): GroupedOpenApi {
        return GroupedOpenApi.builder()
            .group("public-api")
            .pathsToMatch("/**")
            .addOpenApiCustomizer() { openApi ->
                openApi.components?.addSecuritySchemes(
                    "bearerAuth",
                    SecurityScheme().apply {
                        type = SecurityScheme.Type.HTTP
                        scheme = "bearer"
                        bearerFormat = "JWT"
                    }
                )
                openApi.addSecurityItem(
                    SecurityRequirement().addList("bearerAuth")
                )
            }
            .addOpenApiCustomizer { openApi ->
                // /auth/** 경로에 대한 보안 요구 사항 제거
                openApi.paths?.forEach { (path, pathItem) ->
                    if (path.startsWith("/api/v1/auth")) {
                        pathItem.readOperations()?.forEach { operation ->
                            operation.security?.clear()
                        }
                    }
                }
            }
            .build()
    }

    @Bean
    fun passwordEncoder(): PasswordEncoder = BCryptPasswordEncoder()
}
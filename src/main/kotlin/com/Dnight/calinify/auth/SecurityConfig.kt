package com.dnight.calinify.auth

import jakarta.servlet.DispatcherType
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.http.HttpMethod
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.web.DefaultSecurityFilterChain
import org.springframework.security.web.SecurityFilterChain

@EnableWebSecurity
@Configuration
class SecurityConfig {

    @Bean
    fun securityFilterChain(http: HttpSecurity): DefaultSecurityFilterChain? {
        http
            .authorizeHttpRequests { auth ->
                auth
                    .requestMatchers("/v3/**", "/swagger-ui/**").permitAll()
                    .anyRequest().authenticated()
            }
            // resource server 활성화
            .oauth2ResourceServer { oauth -> oauth.jwt { } }
        return http.build()
    }
}
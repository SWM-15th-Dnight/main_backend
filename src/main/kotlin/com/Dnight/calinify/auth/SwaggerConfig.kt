package com.Dnight.calinify.auth

//import io.swagger.v3.oas.models.Components
//import io.swagger.v3.oas.models.OpenAPI
//import io.swagger.v3.oas.models.security.SecurityRequirement
//import io.swagger.v3.oas.models.security.SecurityScheme
//import org.springframework.context.annotation.Bean
//import org.springframework.context.annotation.Configuration
//
//@Configuration
//class SwaggerConfig {
//    @Bean
//    fun customizeOpenAPI(): OpenAPI {
//        return OpenAPI()
//            .addSecurityItem(
//                SecurityRequirement()
//                    .addList(SwaggerConfig.Companion.OAUTH_SCHEME_BEARER)
//            )
//            .components(
//                Components()
//                    .addSecuritySchemes(
//                        SwaggerConfig.Companion.OAUTH_SCHEME_BEARER, SecurityScheme()
//                            .name(SwaggerConfig.Companion.OAUTH_SCHEME_BEARER)
//                            .type(SecurityScheme.Type.HTTP)
//                            .scheme("bearer")
//                            .bearerFormat("JWT")
//                    )
//            )
//    }
//
//    companion object {
//        private const val OAUTH_SCHEME_BEARER = "bearerAuth"
//    }
//}
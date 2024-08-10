package com.dnight.calinify.auth.dto.response

data class TokenResponseDTO(
    val tokenType: String? = "bearer",
    val accessToken: String,
    val refreshToken: String
)
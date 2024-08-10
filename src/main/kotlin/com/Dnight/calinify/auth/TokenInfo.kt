package com.dnight.calinify.auth

data class TokenInfo(
    val grantType: String,
    val accessToken: String,
)
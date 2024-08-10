package com.dnight.calinify.auth.dto.request

import jakarta.validation.constraints.Email
import jakarta.validation.constraints.NotBlank

data class FormLoginDTO(
    @field:Email
    val email: String,

    @field:NotBlank
    val password: String
)
package com.dnight.calinify.user.dto.request

import jakarta.persistence.EnumType
import jakarta.persistence.Enumerated
import jakarta.validation.constraints.Email
import jakarta.validation.constraints.Min
import jakarta.validation.constraints.NotEmpty

data class UserCreateRequestDTO(
    @field:Email
    val email : String,
    @field:NotEmpty
    val password : String,
    @field:NotEmpty
    val userName : String,
    @Enumerated(EnumType.STRING)
    val gender : Gender?,
    val phoneNumber : String?,
)

enum class Gender{
    male, female
}
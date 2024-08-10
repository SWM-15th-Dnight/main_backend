package com.dnight.calinify.auth.dto.request

import com.dnight.calinify.user.entity.AccountLinkEntity
import com.dnight.calinify.user.entity.UserEntity
import jakarta.persistence.EnumType
import jakarta.persistence.Enumerated
import jakarta.validation.constraints.Email
import jakarta.validation.constraints.NotBlank

data class GoogleUserCreateDTO(
    @field:Email
    val email: String,

    @field:NotBlank
    val name: String,

    @field:NotBlank
    val uid: String,

    @Enumerated(EnumType.STRING)
    val gender: GenderEnum? = null,

    val phoneNumber : String? = null,
) {
    companion object {
        fun toEntity(data : GoogleUserCreateDTO,
                     accountLink : AccountLinkEntity) : UserEntity {
            return UserEntity(
                email = data.email,
                userName = data.name,
                gender = data.gender,
                phoneNumber = data.phoneNumber,
                accountLink = accountLink
            )
        }
    }
}
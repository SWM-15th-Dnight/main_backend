package com.dnight.calinify.auth.dto.request

import com.dnight.calinify.user.entity.AccountLinkEntity
import com.dnight.calinify.user.entity.UserEntity
import jakarta.persistence.EnumType
import jakarta.persistence.Enumerated
import jakarta.validation.constraints.Email
import jakarta.validation.constraints.NotEmpty
import jakarta.validation.constraints.Pattern

class UserCreateRequestDTO(
    @field:Email
    val email : String,

    @field:NotEmpty
    @field:Pattern(regexp = "^(?=.*[A-Za-z])(?=.*[\\W_])[A-Za-z\\d\\W_]{8,20}\$")
    var password : String,

    @field:NotEmpty
    val userName : String,

    @Enumerated(EnumType.STRING)
    val gender : GenderEnum?,

    val phoneNumber : String?,
)  {
    companion object{
        fun toEntity(userCreateRequestDTO: UserCreateRequestDTO,
                     accountLink : AccountLinkEntity): UserEntity {
            return UserEntity(
                email = userCreateRequestDTO.email,
                password = userCreateRequestDTO.password,
                userName = userCreateRequestDTO.userName,
                gender = userCreateRequestDTO.gender,
                phoneNumber = userCreateRequestDTO.phoneNumber,
                accountLink = accountLink
            )
        }
    }
}
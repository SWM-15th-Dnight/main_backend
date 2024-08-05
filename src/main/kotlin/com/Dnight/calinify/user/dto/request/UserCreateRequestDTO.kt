package com.dnight.calinify.user.dto.request

import com.dnight.calinify.user.entity.UserEntity
import jakarta.persistence.EnumType
import jakarta.persistence.Enumerated
import jakarta.validation.constraints.Email
import jakarta.validation.constraints.NotEmpty

class UserCreateRequestDTO(
    @field:Email
    val email : String,

    @field:NotEmpty
    val password : String,

    @field:NotEmpty
    val userName : String,

    @Enumerated(EnumType.STRING)
    val gender : GenderEnum?,
    val phoneNumber : String?,
)  {
    companion object{
        fun toEntity(userCreateRequestDTO: UserCreateRequestDTO): UserEntity {
            return UserEntity(
                email = userCreateRequestDTO.email,
                password = userCreateRequestDTO.password,
                userName = userCreateRequestDTO.userName,
                gender = userCreateRequestDTO.gender,
                phoneNumber = userCreateRequestDTO.phoneNumber,
            )
        }
    }
}
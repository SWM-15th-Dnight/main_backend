package com.dnight.calinify.user.dto.response

import com.dnight.calinify.user.entity.UserEntity

class UserCreateResponseDTO(
    val userName: String,
    val email: String,
) {
    companion object {
        fun from(user: UserEntity) = UserCreateResponseDTO(
            userName = user.userName,
            email = user.email
        )
    }
}
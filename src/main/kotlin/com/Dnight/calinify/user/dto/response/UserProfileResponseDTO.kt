package com.dnight.calinify.user.dto.response

import com.dnight.calinify.user.dto.request.Gender
import com.dnight.calinify.user.entity.UserEntity

data class UserProfileResponseDTO(
    val userId : Long,
    val userName: String,
    val email: String,
    val phoneNumber: String?,
    val gender: Gender?,
) {
    companion object {
        fun from(user: UserEntity) : UserProfileResponseDTO {
            return UserProfileResponseDTO(
                userId = user.userId!!,
                userName = user.userName,
                email = user.email,
                phoneNumber = user.phoneNumber,
                gender = user.gender
            )
        }
    }
}
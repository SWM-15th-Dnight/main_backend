package com.dnight.calinify.user.service

import com.dnight.calinify.config.basicResponse.ResponseCode
import com.dnight.calinify.config.exception.ClientException
import com.dnight.calinify.user.dto.response.UserProfileResponseDTO
import com.dnight.calinify.user.repository.UserRepository
import org.springframework.stereotype.Service

@Service
class UserService(
    private val userRepository: UserRepository,
) {
    fun getUser(userId: Long) : UserProfileResponseDTO {
        val user = userRepository.findById(userId).orElse(null)
            ?: throw ClientException(ResponseCode.UserNotFound)
        return UserProfileResponseDTO.from(user)
    }
}
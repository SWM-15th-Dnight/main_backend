package com.dnight.calinify.user.service

import com.dnight.calinify.config.basicResponse.ResponseCode
import com.dnight.calinify.config.exception.ClientException
import com.dnight.calinify.user.dto.request.UserCreateRequestDTO
import com.dnight.calinify.user.dto.response.UserCreateResponseDTO
import com.dnight.calinify.user.dto.response.UserProfileResponseDTO
import com.dnight.calinify.user.entity.UserEntity
import com.dnight.calinify.user.repository.UserRepository
import jakarta.transaction.Transactional
import org.springframework.stereotype.Service

@Service
class UserService(private val userRepository: UserRepository) {

    @Transactional
    fun createUser(userCreateRequestDTO: UserCreateRequestDTO) : UserCreateResponseDTO {
        val newUser = UserCreateRequestDTO.toEntity(userCreateRequestDTO)
        userRepository.save(newUser)
        return UserCreateResponseDTO.from(newUser)
    }

    fun getUser(userId: Long) : UserProfileResponseDTO {
        val user = userRepository.findById(userId).orElse(null) ?: throw ClientException(ResponseCode.UserNotFound)
        return UserProfileResponseDTO.from(user)
    }
}
package com.dnight.calinify.user.service

import com.dnight.calinify.config.basicResponse.ResponseCode
import com.dnight.calinify.config.exception.ClientException
import com.dnight.calinify.user.dto.request.GoogleUserCreateDTO
import com.dnight.calinify.user.dto.request.UserCreateRequestDTO
import com.dnight.calinify.user.dto.response.UserCreateResponseDTO
import com.dnight.calinify.user.dto.response.UserProfileResponseDTO
import com.dnight.calinify.user.entity.AccountLinkEntity
import com.dnight.calinify.user.repository.UserRepository
import jakarta.transaction.Transactional
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service

@Service
class UserService(
    private val userRepository: UserRepository,
    private val passwordEncoder: PasswordEncoder
) {
    @Transactional
    fun createUser(userCreateRequestDTO: UserCreateRequestDTO) : UserCreateResponseDTO {
        val accountLink = AccountLinkEntity()

        // 비밀번호 암호화
        userCreateRequestDTO.password = passwordEncoder.encode(userCreateRequestDTO.password)

        val newUser = UserCreateRequestDTO.toEntity(userCreateRequestDTO, accountLink)
        userRepository.save(newUser)
        return UserCreateResponseDTO.from(newUser)
    }

    @Transactional
    fun createGoogleUser(googleUserCreateDTO: GoogleUserCreateDTO) : UserCreateResponseDTO {
        val accountLink = AccountLinkEntity(google = googleUserCreateDTO.uid)

        val newUser = GoogleUserCreateDTO.toEntity(googleUserCreateDTO, accountLink)
        userRepository.save(newUser)

        return UserCreateResponseDTO.from(newUser)
    }

    fun getUser(userId: Long) : UserProfileResponseDTO {
        val user = userRepository.findById(userId).orElse(null) ?: throw ClientException(ResponseCode.UserNotFound)
        return UserProfileResponseDTO.from(user)
    }
}
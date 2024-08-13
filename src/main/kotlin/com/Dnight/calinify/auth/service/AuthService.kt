package com.dnight.calinify.auth.service

import com.dnight.calinify.auth.dto.request.FormLoginDTO
import com.dnight.calinify.auth.dto.request.GoogleUserCreateDTO
import com.dnight.calinify.auth.dto.request.UserCreateRequestDTO
import com.dnight.calinify.auth.dto.response.TokenResponseDTO
import com.dnight.calinify.auth.dto.response.UserCreateResponseDTO
import com.dnight.calinify.auth.jwt.JwtTokenProvider
import com.dnight.calinify.user.entity.AccountLinkEntity
import com.dnight.calinify.user.repository.AccountLinkRepository
import com.dnight.calinify.user.repository.UserRepository
import jakarta.transaction.Transactional
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service

@Service
class AuthService(
    private val authenticationManagerBuilder: AuthenticationManagerBuilder,
    private val userRepository: UserRepository,
    private val passwordEncoder: PasswordEncoder,
    private val jwtTokenProvider: JwtTokenProvider,
    private val accountLinkRepository: AccountLinkRepository
) {

    fun login(formLoginDTO: FormLoginDTO): TokenResponseDTO {

        val authenticationToken = UsernamePasswordAuthenticationToken(formLoginDTO.email, formLoginDTO.password)

        val authentication = authenticationManagerBuilder.`object`.authenticate(authenticationToken)

        val jwt = jwtTokenProvider.createToken(authentication)

        return TokenResponseDTO("bearer", jwt.accessToken, jwt.refreshToken)
    }

    @Transactional
    fun createUser(userCreateRequestDTO: UserCreateRequestDTO) : UserCreateResponseDTO {
        val accountLinkEntity = AccountLinkEntity()
        val accountLink = accountLinkRepository.save(accountLinkEntity)

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
}
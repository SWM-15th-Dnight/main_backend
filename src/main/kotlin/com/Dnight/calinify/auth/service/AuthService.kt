package com.dnight.calinify.auth.service

import com.dnight.calinify.auth.dto.request.FormLoginDTO
import com.dnight.calinify.auth.dto.request.GoogleUserCreateDTO
import com.dnight.calinify.auth.dto.request.UserCreateRequestDTO
import com.dnight.calinify.auth.dto.response.TokenResponseDTO
import com.dnight.calinify.auth.dto.response.UserCreateResponseDTO
import com.dnight.calinify.auth.jwt.JwtTokenProvider
import com.dnight.calinify.calendar.dto.request.CalendarCreateDTO
import com.dnight.calinify.calendar.entity.CalendarEntity
import com.dnight.calinify.calendar.repository.CalendarRepository
import com.dnight.calinify.config.basicResponse.ResponseCode
import com.dnight.calinify.config.exception.ClientException
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
    private val accountLinkRepository: AccountLinkRepository,
    private val calendarRepository: CalendarRepository
) {

    fun login(formLoginDTO: FormLoginDTO): TokenResponseDTO {

        val authenticationToken = UsernamePasswordAuthenticationToken(formLoginDTO.email, formLoginDTO.password)

        val authentication = authenticationManagerBuilder.`object`.authenticate(authenticationToken)

        val jwt = jwtTokenProvider.createToken(authentication)

        return TokenResponseDTO("bearer", jwt.accessToken, jwt.refreshToken)
    }

    @Transactional
    fun createUser(userCreateRequestDTO: UserCreateRequestDTO) : UserCreateResponseDTO {

        // 비밀번호의 패턴 확인은 DTO 단계에서 처리

        if (userRepository.existsByEmail(userCreateRequestDTO.email))
            throw ClientException(ResponseCode.DuplicatedInputData, "email")

        // 비밀번호 암호화
        userCreateRequestDTO.password = passwordEncoder.encode(userCreateRequestDTO.password)

        val user = UserCreateRequestDTO.toEntity(userCreateRequestDTO)

        val newUser = userRepository.save(user)

        val accountLinkEntity = AccountLinkEntity(newUser.userId!!, newUser, null)

        newUser.addAccountLink(accountLinkEntity)

        return UserCreateResponseDTO.from(newUser)
    }

    @Transactional
    fun createGoogleUser(googleUserCreateDTO: GoogleUserCreateDTO) : UserCreateResponseDTO {

        val googleUserPassword = passwordEncoder.encode(googleUserCreateDTO.uid)

        val user = GoogleUserCreateDTO.toEntity(googleUserCreateDTO, googleUserPassword)
        val newUser = userRepository.save(user)

        val accountLink = AccountLinkEntity(newUser.userId!!, newUser, google = googleUserCreateDTO.uid)
        accountLinkRepository.save(accountLink)

        val defaultCalendar : CalendarEntity = CalendarCreateDTO.toEntity(
            CalendarCreateDTO("일정", "기본 캘린더입니다.", "Asia/Seoul", 1), user)
        calendarRepository.save(defaultCalendar)

        return UserCreateResponseDTO.from(newUser)
    }
}
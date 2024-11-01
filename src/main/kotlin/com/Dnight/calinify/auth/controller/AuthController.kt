package com.dnight.calinify.auth.controller

import com.dnight.calinify.auth.dto.request.FormLoginDTO
import com.dnight.calinify.auth.dto.request.GoogleUserCreateDTO
import com.dnight.calinify.auth.dto.request.UserCreateRequestDTO
import com.dnight.calinify.auth.dto.response.TokenResponseDTO
import com.dnight.calinify.auth.dto.response.UserCreateResponseDTO
import com.dnight.calinify.auth.service.AuthService
import com.dnight.calinify.config.basicResponse.BasicResponse
import com.dnight.calinify.config.basicResponse.ResponseCode
import jakarta.validation.Valid
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RequestMapping("/api/v1/auth")
@RestController
@Validated
class AuthController(
    private val authService: AuthService
) {
    @PostMapping("/login")
    fun login(@RequestBody @Valid formLoginDTO: FormLoginDTO) : TokenResponseDTO {
        val tokenResponseDTO = authService.login(formLoginDTO)
        return tokenResponseDTO
    }

    @PostMapping("/google")
    fun createGoogleUser(@Valid @RequestBody googleUserCreateDTO: GoogleUserCreateDTO) : BasicResponse<UserCreateResponseDTO> {
        val newGoogleUser = authService.createGoogleUser(googleUserCreateDTO)

        return BasicResponse.ok(newGoogleUser, ResponseCode.CreateSuccess)
    }

    @PostMapping("/")
    fun createUser(@Valid @RequestBody userCreateDTO: UserCreateRequestDTO) : BasicResponse<UserCreateResponseDTO> {
        val newUser = authService.createUser(userCreateDTO)
        return BasicResponse.ok(newUser, ResponseCode.CreateSuccess)
    }
}
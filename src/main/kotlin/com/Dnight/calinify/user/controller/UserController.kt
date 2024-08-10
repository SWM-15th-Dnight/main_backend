package com.dnight.calinify.user.controller

import com.dnight.calinify.config.basicResponse.BasicResponse
import com.dnight.calinify.config.basicResponse.ResponseCode
import com.dnight.calinify.user.dto.request.GoogleUserCreateDTO
import com.dnight.calinify.user.dto.request.UserCreateRequestDTO
import com.dnight.calinify.user.dto.response.UserCreateResponseDTO
import com.dnight.calinify.user.dto.response.UserProfileResponseDTO
import com.dnight.calinify.user.service.UserService
import jakarta.validation.Valid
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("api/v1/user")
class UserController(val userService: UserService) {

    @PostMapping("/")
    fun createUser(@Valid @RequestBody userCreateDTO: UserCreateRequestDTO) : BasicResponse<UserCreateResponseDTO> {
        val newUser = userService.createUser(userCreateDTO)
        return BasicResponse.ok(newUser, ResponseCode.CreateSuccess)
    }

    @PostMapping("/google")
    fun createGoogleUser(@Valid @RequestBody googleUserCreateDTO: GoogleUserCreateDTO) :BasicResponse<UserCreateResponseDTO> {
        val newGoogleUser = userService.createGoogleUser(googleUserCreateDTO)

        return BasicResponse.ok(newGoogleUser, ResponseCode.CreateSuccess)
    }

    @GetMapping("/{userId}")
    fun getUserById(@PathVariable userId: Long) : BasicResponse<UserProfileResponseDTO> {
        val user = userService.getUser(userId)
        return BasicResponse.ok(user, ResponseCode.ResponseSuccess)
    }
}
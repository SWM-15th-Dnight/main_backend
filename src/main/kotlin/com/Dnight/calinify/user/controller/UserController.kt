package com.dnight.calinify.user.controller

import com.dnight.calinify.config.basicResponse.BasicResponse
import com.dnight.calinify.config.basicResponse.ResponseCode
import com.dnight.calinify.user.dto.response.UserProfileResponseDTO
import com.dnight.calinify.user.service.UserService
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@Validated
@RestController
@RequestMapping("api/v1/user")
class UserController(val userService: UserService) {

    @GetMapping("/{userId}")
    fun getUserById(@PathVariable userId: Long) : BasicResponse<UserProfileResponseDTO> {
        val user = userService.getUser(userId)
        return BasicResponse.ok(user, ResponseCode.ResponseSuccess)
    }
}
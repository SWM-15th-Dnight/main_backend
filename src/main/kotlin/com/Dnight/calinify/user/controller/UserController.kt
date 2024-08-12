package com.dnight.calinify.user.controller

import com.dnight.calinify.config.basicResponse.BasicResponse
import com.dnight.calinify.config.basicResponse.ResponseCode
import com.dnight.calinify.user.dto.response.UserProfileResponseDTO
import com.dnight.calinify.user.service.UserService
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@Validated
@RestController
@RequestMapping("api/v1/user")
class UserController(val userService: UserService) {

    @GetMapping("/")
    fun getUserById(@AuthenticationPrincipal userDetails: UserDetails,
                    ) : BasicResponse<UserProfileResponseDTO> {
        val user = userService.getUser(userDetails.username.toLong())
        return BasicResponse.ok(user, ResponseCode.ResponseSuccess)
    }
}
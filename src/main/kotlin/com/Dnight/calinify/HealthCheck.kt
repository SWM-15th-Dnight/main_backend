package com.dnight.calinify

import com.dnight.calinify.config.basicResponse.BasicResponse
import com.dnight.calinify.config.basicResponse.ResponseCode
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import java.time.LocalDateTime

@RestController
@RequestMapping("/")
class HealthCheck {

    @GetMapping("")
    fun healthCheck() : BasicResponse<Map<String, String>> {
        val healthCheckResponse : Map<String, String> = mutableMapOf<String, String> (
            "Calinify" to "Hello World",
            "HealthCheck" to "Ok",
            "ServerTime" to LocalDateTime.now().toString(),
            )
        return BasicResponse(healthCheckResponse, ResponseCode.ResponseSuccess)
    }
}
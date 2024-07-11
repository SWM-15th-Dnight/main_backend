package com.dnight.calinify.calendar

import com.dnight.calinify.config.basicResponse.BasicResponse
import com.dnight.calinify.config.basicResponse.ResponseCode
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/calendar")
class Controller{
    @GetMapping("/test")
    fun testResponse() : Map<String, String> {
        ResponseEntity.ok().body(BasicResponse.body(ResponseCode.Unauthorized, mapOf("test" to "success")))
        return mapOf("test" to "success")
    }
}

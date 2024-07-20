package com.dnight.calinify.auth;

import org.springframework.security.core.Authentication
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping(value = ["/auth"])
public class controller {
    @GetMapping(value = ["/uid"])
    fun uid(authentication: Authentication): String {
        return authentication.name
    }

    @GetMapping(value = ["/hello"])
    fun hello(): String {
        return "hello world"
    }
}
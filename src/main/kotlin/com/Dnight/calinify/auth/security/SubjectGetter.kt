package com.dnight.calinify.auth.security

import org.springframework.security.core.Authentication
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.security.core.userdetails.UserDetails

class SubjectGetter {
    fun getSubject(): String {
        val authentication: Authentication = SecurityContextHolder.getContext().authentication

        val subject: String = when (val principal = authentication.principal) {
            is UserDetails -> principal.username
            is String -> principal
            else -> "Unknown"
        }

        return subject
    }
}
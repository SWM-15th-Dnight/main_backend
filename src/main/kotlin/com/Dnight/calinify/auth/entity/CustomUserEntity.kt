package com.dnight.calinify.auth.entity

import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.userdetails.User

class CustomUserEntity(
    val userId : Long,
    userEmail : String,
    password : String,
    role : Collection<GrantedAuthority>
) : User(userEmail, password, role)
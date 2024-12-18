package com.dnight.calinify.user.repository

import com.dnight.calinify.user.entity.UserEntity
import org.springframework.data.jpa.repository.JpaRepository

interface UserRepository : JpaRepository<UserEntity, Long> {

    fun existsByEmail(userEmail: String): Boolean

    fun findByEmail(userEmail: String): UserEntity?
}
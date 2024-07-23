package com.dnight.calinify.user.entity

import com.dnight.calinify.user.dto.request.Gender
import com.dnight.calinify.user.dto.request.UserCreateRequestDTO
import jakarta.persistence.*
import jakarta.validation.constraints.Email
import org.springframework.data.annotation.CreatedDate
import org.springframework.data.annotation.LastModifiedDate
import java.time.LocalDateTime

@Entity
@Table(name = "user")
data class UserEntity (

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    val userId: Long? = null,

    @Email @Column(nullable = false, unique = true)
    val email: String,

    @Column(nullable = false)
    var userName: String,

    @Column(nullable = false)
    val password: String,

    @CreatedDate
    val createdAt : LocalDateTime? = LocalDateTime.now(),

    @LastModifiedDate
    val updatedAt : LocalDateTime? = LocalDateTime.now(),

    @Enumerated(EnumType.STRING)
    val gender: Gender?,

    val phoneNumber: String?
)
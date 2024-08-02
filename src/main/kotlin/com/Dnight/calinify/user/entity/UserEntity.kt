package com.dnight.calinify.user.entity

import com.dnight.calinify.user.dto.request.Gender
import jakarta.persistence.*
import jakarta.validation.constraints.Email
import org.springframework.data.annotation.CreatedDate
import org.springframework.data.annotation.LastModifiedDate
import java.time.LocalDateTime

@Entity
@Table(name = "user")
class UserEntity (

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    var userId: Long? = null,

    @Email @Column(nullable = false, unique = true)
    var email: String,

    @Column(nullable = false)
    var userName: String,

    @Column(nullable = false)
    val password: String,

    @CreatedDate
    var createdAt : LocalDateTime? = LocalDateTime.now(),

    @LastModifiedDate
    var updatedAt : LocalDateTime? = LocalDateTime.now(),

    @Enumerated(EnumType.STRING)
    var gender: Gender?,

    val phoneNumber: String?
)
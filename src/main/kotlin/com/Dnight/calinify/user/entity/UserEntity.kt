package com.dnight.calinify.user.entity

import com.dnight.calinify.config.basicEntity.BasicEntity
import com.dnight.calinify.user.dto.request.GenderEnum
import jakarta.persistence.*
import jakarta.validation.constraints.Email

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

    @Enumerated(EnumType.STRING)
    var gender: GenderEnum?,

    val phoneNumber: String?
) : BasicEntity()
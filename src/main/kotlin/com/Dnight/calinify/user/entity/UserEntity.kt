package com.dnight.calinify.user.entity

import com.dnight.calinify.auth.dto.request.GenderEnum
import com.dnight.calinify.config.basicEntity.BasicEntity
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

    @Column(nullable = true)
    val password: String? = null,

    @Enumerated(EnumType.STRING)
    var gender: GenderEnum? = null,

    val phoneNumber: String? = null,

    @OneToOne
    @JoinColumn(name = "account_link_id", nullable = true)
    val accountLink : AccountLinkEntity? = null,

    val isActivated : Short = 1,

    val role: String = "COMMON"
) : BasicEntity()
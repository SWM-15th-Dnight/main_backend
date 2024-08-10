package com.dnight.calinify.user.entity

import jakarta.persistence.*

@Entity
@Table(name = "account_link")
class AccountLinkEntity(
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    val accountLinkId: Long = 0,

    var google : String? = null,

    var microsoft : String? = null,
)
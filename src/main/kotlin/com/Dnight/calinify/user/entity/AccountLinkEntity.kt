package com.dnight.calinify.user.entity

import jakarta.persistence.*

@Entity
@Table(name = "account_link")
class AccountLinkEntity(
    @Id
    var userId: Long? = null,

    @MapsId
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "userId")
    var user: UserEntity,

    var google : String? = null,

    var microsoft : String? = null,
)
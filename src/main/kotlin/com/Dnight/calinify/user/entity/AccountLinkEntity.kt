package com.dnight.calinify.user.entity

import jakarta.persistence.*

@Entity
@Table(name = "account_link")
class AccountLinkEntity(
    @Id
    var userId: Long? = null,

    @MapsId("userId")
    @OneToOne(targetEntity = UserEntity::class, fetch = FetchType.LAZY, cascade = [CascadeType.ALL])
    @JoinColumn(name = "userId")
    var user: UserEntity,

    var google : String? = null,
)
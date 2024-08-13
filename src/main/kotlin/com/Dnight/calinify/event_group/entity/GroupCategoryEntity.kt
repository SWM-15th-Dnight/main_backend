package com.dnight.calinify.event_group.entity

import jakarta.persistence.*


@Entity
@Table(name = "group_category")
class GroupCategoryEntity(
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    val groupCategoryId : Long? = null,

    @Column(nullable = false)
    val title : String,
)
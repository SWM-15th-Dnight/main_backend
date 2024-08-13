package com.dnight.calinify.common.colorSet

import jakarta.persistence.*

@Entity
@Table(name = "color_set")
class ColorSetEntity(

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    val colorSetId : Int? = null,

    @Column(nullable = false, length = 20)
    val colorName : String,

    @Column(nullable = false, length = 20)
    val hexCode : String,
)
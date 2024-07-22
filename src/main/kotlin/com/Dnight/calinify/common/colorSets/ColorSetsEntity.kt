package com.dnight.calinify.common.colorSets

import jakarta.persistence.*

@Table(name = "color_sets")
@Entity
data class ColorSetsEntity(

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    val colorSetId : Long = 0,

    @Column(nullable = false, length = 20)
    val colorName : String,

    @Column(nullable = false, length = 20)
    val hexCode : String,
)
package com.dnight.calinify.common.inputType

import jakarta.persistence.*

@Entity
@Table(name = "input_type")
data class InputTypeEntity(

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    val inputTypeId: Long,

    @Column(nullable = false, length = 50)
    val inputType: String
)
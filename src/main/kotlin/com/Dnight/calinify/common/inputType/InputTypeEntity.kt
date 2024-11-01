package com.dnight.calinify.common.inputType

import jakarta.persistence.*

@Entity
@Table(name = "input_type")
data class InputTypeEntity(

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    val inputTypeId: Int? = null,

    @Column(nullable = false, length = 50)
    var inputType: String? = null
)
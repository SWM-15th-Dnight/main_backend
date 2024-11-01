package com.dnight.calinify.common.inputType

import jakarta.validation.constraints.NotBlank

class InputTypeRequestDTO(

    @field:NotBlank
    val inputType : String
)
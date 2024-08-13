package com.dnight.calinify.common.inputType

class InputTypeResponseDTO(
    val inputTypeId : Int,

    val inputType : String,
) {
    companion object {
        fun from(inputTypeEntity: InputTypeEntity) : InputTypeResponseDTO {
            return InputTypeResponseDTO(
                inputTypeId = inputTypeEntity.inputTypeId!!,
                inputType = inputTypeEntity.inputType)
        }
    }
}
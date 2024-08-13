package com.dnight.calinify.common.colorSet

data class ColorSetResponseDTO(
    val colorSetId : Int,

    val colorName : String,

    val hexCode : String,
) {
    companion object {
        fun from(colorSetEntity : ColorSetEntity) : ColorSetResponseDTO {
            return ColorSetResponseDTO(
                colorSetId = colorSetEntity.colorSetId!!,
                colorName = colorSetEntity.colorName,
                hexCode = colorSetEntity.hexCode,
            )
        }
    }
}
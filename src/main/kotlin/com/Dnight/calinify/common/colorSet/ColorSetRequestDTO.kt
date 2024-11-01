package com.dnight.calinify.common.colorSet

import jakarta.validation.constraints.Min
import jakarta.validation.constraints.NotBlank

class ColorSetRequestDTO(
    @field:Min(1)
    val colorSetId : Int? = null,

    @field:NotBlank
    val colorSetName : String,

    @field:NotBlank
    val hexCode : String
) {
    companion object {
        fun toEntity(colorSetRequestDTO: ColorSetRequestDTO) : ColorSetEntity {
            return ColorSetEntity(
                colorSetId = colorSetRequestDTO.colorSetId,
                colorName = colorSetRequestDTO.colorSetName,
                hexCode = colorSetRequestDTO.hexCode,
            )
        }
    }
}
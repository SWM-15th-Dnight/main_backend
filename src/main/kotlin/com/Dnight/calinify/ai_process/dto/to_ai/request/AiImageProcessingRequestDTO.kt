package com.dnight.calinify.ai_process.dto.to_ai.request

import com.dnight.calinify.ai_process.dto.request.ImageProcessingRequestDTO

class AiImageProcessingRequestDTO(
    override val promptId: Int,
    val inputType : Int,
): AiRequestDTO() {
    companion object {
        fun toDTO(imageProcessingRequestDTO: ImageProcessingRequestDTO) : AiImageProcessingRequestDTO {
            return AiImageProcessingRequestDTO(
                promptId = imageProcessingRequestDTO.promptId,
                inputType = imageProcessingRequestDTO.inputType
            )
        }
    }
}
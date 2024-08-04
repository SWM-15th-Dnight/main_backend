package com.dnight.calinify.ai_process.dto.to_ai.request

import com.dnight.calinify.ai_process.dto.request.PlainTextProcessingRequestDTO

class AiPlainTextProcessingRequestDTO(
    override val promptId : Int,
    val plainText : String
) : AiRequestDTO() {
    companion object {
        fun toEntity(plainTextProcessingRequestDTO: PlainTextProcessingRequestDTO) : AiPlainTextProcessingRequestDTO {
            return AiPlainTextProcessingRequestDTO(
                plainTextProcessingRequestDTO.promptId,
                plainTextProcessingRequestDTO.originText
            )
        }
    }
}
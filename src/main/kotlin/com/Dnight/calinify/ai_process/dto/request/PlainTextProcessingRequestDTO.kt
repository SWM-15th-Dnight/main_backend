package com.dnight.calinify.ai_process.dto.request

import com.dnight.calinify.ai_process.dto.to_ai.request.AiRequestDTO
import jakarta.validation.constraints.Min
import jakarta.validation.constraints.NotEmpty
import jakarta.validation.constraints.Size

data class PlainTextProcessingRequestDTO(
    @field:Min(1)
    val promptId : Int = 1,

    @field:NotEmpty
    @field:Size(max = 2047)
    val originText: String,

    @field:Min(1)
    val inputType : Int,
)
package com.dnight.calinify.ai_process.dto.to_ai.response

import java.time.LocalDateTime

open class AiResponseDTO(
    val summary : String,
    val start : LocalDateTime?,
    val end : LocalDateTime?,
    val responseTime : Float,
    val usedToken : Int,
    val location : String?,
    val description : String?,
    val priority : Short? = 5,
    val repeatRule : String?,
)
package com.dnight.calinify.ai_process.dto.response

import com.dnight.calinify.ai_process.dto.to_ai.response.AiPlainTextProcessedResponseDTO
import java.time.LocalDateTime

data class ProcessedEventResponseDTO(
    val processedEventId : Long,
    val summary : String,
    val start : LocalDateTime,
    val end : LocalDateTime,
    val responseTime : Float,
    val usedToken : Int,
    val location : String?,
    val description : String?,
    val priority : Short? = 5,
    val repeatRule : String?,
) {
    companion object {
        fun from(processedEventId: Long, processedResponseBody : AiPlainTextProcessedResponseDTO) : ProcessedEventResponseDTO {
            return ProcessedEventResponseDTO(
                processedEventId = processedEventId,
                summary = processedResponseBody.summary,
                start = processedResponseBody.start!!,
                end = processedResponseBody.end!!,
                responseTime = processedResponseBody.responseTime,
                usedToken = processedResponseBody.usedToken,
                location = processedResponseBody.location,
                description = processedResponseBody.description,
                priority = processedResponseBody.priority,
                repeatRule = processedResponseBody.repeatRule,
            )
        }
    }
}
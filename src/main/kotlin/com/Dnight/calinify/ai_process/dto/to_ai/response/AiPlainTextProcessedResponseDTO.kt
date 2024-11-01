package com.dnight.calinify.ai_process.dto.to_ai.response

import com.dnight.calinify.ai_process.dto.request.PlainTextProcessingRequestDTO
import com.dnight.calinify.ai_process.entity.AiProcessingEventEntity
import com.dnight.calinify.ai_process.entity.AiProcessingStatisticsEntity
import com.dnight.calinify.user.entity.UserEntity
import java.time.LocalDateTime

class AiPlainTextProcessedResponseDTO(
    summary: String,
    start: LocalDateTime?,
    end: LocalDateTime?,
    responseTime: Float,
    usedToken: Int,
    location: String?,
    description: String?,
    priority: Short? = 5,
    repeatRule: String?
) : AiResponseDTO(summary, start, end, responseTime, usedToken, location, description, priority, repeatRule)
{
    companion object {
        fun toStatisticsEntity(userEntity : UserEntity,
                     aiPlainTextProcessedResponseDTO: AiPlainTextProcessedResponseDTO,
                     inputData: PlainTextProcessingRequestDTO): AiProcessingStatisticsEntity {
            return AiProcessingStatisticsEntity(
                user = userEntity,
                inputOriginText = inputData.originText,
                promptId = inputData.promptId,
                inputTypeId = inputData.inputType,
                responseTime = aiPlainTextProcessedResponseDTO.responseTime,
                usedToken = aiPlainTextProcessedResponseDTO.usedToken,
            )
        }

        fun toEventEntity(eventProcessedStatisticsId : Long,
                 aiPlainTextProcessedResponseDTO: AiPlainTextProcessedResponseDTO
        ) : AiProcessingEventEntity {
            return AiProcessingEventEntity(
                aiProcessingEventId = eventProcessedStatisticsId,
                summary = aiPlainTextProcessedResponseDTO.summary,
                startAt = aiPlainTextProcessedResponseDTO.start!!,
                endAt = aiPlainTextProcessedResponseDTO.end!!,
                description = aiPlainTextProcessedResponseDTO.description,
                location = aiPlainTextProcessedResponseDTO.location,
                repeatRule = aiPlainTextProcessedResponseDTO.repeatRule,
            )
        }
    }
}
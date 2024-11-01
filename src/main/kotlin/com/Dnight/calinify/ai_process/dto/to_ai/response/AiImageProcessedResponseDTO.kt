package com.dnight.calinify.ai_process.dto.to_ai.response

import com.dnight.calinify.ai_process.dto.request.ImageProcessingRequestDTO
import com.dnight.calinify.ai_process.entity.AiProcessingEventEntity
import com.dnight.calinify.ai_process.entity.AiProcessingStatisticsEntity
import com.dnight.calinify.user.entity.UserEntity
import java.time.LocalDateTime

class AiImageProcessedResponseDTO(
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
                               aiImageProcessedResponseDTO: AiImageProcessedResponseDTO,
                               imageUUID : String,
                               inputData: ImageProcessingRequestDTO
        ): AiProcessingStatisticsEntity {
            return AiProcessingStatisticsEntity(
                user = userEntity,
                inputOriginText = imageUUID,
                promptId = inputData.promptId,
                inputTypeId = inputData.inputType,
                responseTime = aiImageProcessedResponseDTO.responseTime,
                usedToken = aiImageProcessedResponseDTO.usedToken,
            )
        }

        fun toEventEntity(eventProcessedStatisticsId : Long,
                          aiImageProcessedResponseDTO: AiImageProcessedResponseDTO,
        ) : AiProcessingEventEntity {
            return AiProcessingEventEntity(
                aiProcessingEventId = eventProcessedStatisticsId,
                summary = aiImageProcessedResponseDTO.summary,
                startAt = aiImageProcessedResponseDTO.start!!,
                endAt = aiImageProcessedResponseDTO.end!!,
                description = aiImageProcessedResponseDTO.description,
                location = aiImageProcessedResponseDTO.location,
                repeatRule = aiImageProcessedResponseDTO.repeatRule,
            )
        }
    }
}
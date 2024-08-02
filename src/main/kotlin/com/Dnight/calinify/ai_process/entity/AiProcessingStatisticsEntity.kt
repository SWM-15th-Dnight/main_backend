package com.dnight.calinify.ai_process.entity

import com.dnight.calinify.ai_process.dto.request.PlainTextProcessingRequestDTO
import com.dnight.calinify.ai_process.dto.to_ai.response.AiPlainTextProcessedResponseDTO
import com.dnight.calinify.event.entity.EventEntity
import com.dnight.calinify.user.entity.UserEntity
import jakarta.persistence.*

@Entity
@Table(name = "ai_processing_statistics")
data class AiProcessingStatisticsEntity(

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    val aiProcessingStatisticsId: Long? = 0,

    @OneToOne
    @JoinColumn(name = "ai_processing_statistics_id")
    val aiProcessingEvent : AiProcessingEventEntity? = null,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    val user: UserEntity,

    @Column(nullable = false, length = 2047)
    val inputOriginText : String,

    @Column(nullable = false)
    val inputType: Int,

    @Column(nullable = false)
    val promptId : Int,

    @Column(nullable = false)
    val responseTime : Float,

    @Column(nullable = false)
    val usedToken : Int,

    // 성공을 기본값으로 잡는다.
    @Column(nullable = false)
    var isSuccess : Short = 1,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "event_id", nullable = true)
    var eventId: EventEntity? = null
) {
    fun addEventId(eventId: EventEntity) {
        this.eventId = eventId
    }

    companion object {
        fun from(userEntity : UserEntity, aiPlainTextProcessedResponseDTO: AiPlainTextProcessedResponseDTO, inputData: PlainTextProcessingRequestDTO): AiProcessingStatisticsEntity {
            return AiProcessingStatisticsEntity(
                user = userEntity,
                inputOriginText = inputData.originText,
                promptId = inputData.promptId,
                inputType = inputData.inputType,
                responseTime = aiPlainTextProcessedResponseDTO.responseTime,
                usedToken = aiPlainTextProcessedResponseDTO.usedToken,
            )
        }
    }
}
package com.dnight.calinify.event.dto.response

import com.dnight.calinify.ai_process.dto.to_ai.response.AiResponseDTO
import com.dnight.calinify.event.entity.EventEntity
import com.dnight.calinify.event.entity.EventStatus
import com.dnight.calinify.event.entity.EventTransp
import java.time.LocalDateTime

class EventResponseDTO(
    val eventId : Long,
    val summary : String,
    val description : String? = null,
    val startAt : LocalDateTime,
    val endAt : LocalDateTime,
    val priority : Short?,
    val location : String? = null,
    val repeatRule : String? = null,
    val status : EventStatus,
    val transp : EventTransp,
    // TODO 알람 추가
    val alarmId : Long? = null
): AiResponseDTO() {
    companion object {
        fun from(event: EventEntity) : EventResponseDTO {
            return EventResponseDTO(
                eventId = event.eventId!!,
                summary = event.summary,
                description = event.description,
                startAt = event.startAt,
                endAt = event.endAt,
                priority = event.priority,
                location = event.location,
                repeatRule = event.repeatRule,
                status = event.status,
                transp = event.transp,
                alarmId = event.alarm
            )
        }
    }
}
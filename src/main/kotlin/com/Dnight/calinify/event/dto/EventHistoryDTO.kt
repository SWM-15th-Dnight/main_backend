package com.dnight.calinify.event.dto

import com.dnight.calinify.event.dto.response.EventResponseDTO
import com.dnight.calinify.event.entity.EventEntity
import com.dnight.calinify.event.entity.EventHistoryEntity

class EventHistoryDTO() {
    companion object {
        fun toEntity(eventData : EventResponseDTO, event: EventEntity): EventHistoryEntity {
            return EventHistoryEntity(
                event = event,
                summary = eventData.summary,
                startAt = eventData.startAt,
                endAt = eventData.endAt,
                description = eventData.description,
                priority = eventData.priority,
                status = eventData.status,
                transp = eventData.transp,
                repeatRule = eventData.repeatRule,
                location = eventData.location,
            )
        }
    }
}
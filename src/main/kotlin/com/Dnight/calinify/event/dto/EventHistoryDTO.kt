package com.dnight.calinify.event.dto

import com.dnight.calinify.event.entity.EventEntity
import com.dnight.calinify.event.entity.EventHistoryEntity

class EventHistoryDTO() {
    companion object {
        fun toEntity(event: EventEntity): EventHistoryEntity {
            return EventHistoryEntity(
                event = event,
                summary = event.summary,
                startAt = event.startAt,
                endAt = event.endAt,
                description = event.description,
                priority = event.priority,
                status = event.status,
                transp = event.transp,
                repeatRule = event.repeatRule,
                location = event.location,
            )
        }
    }
}
package com.dnight.calinify.event.dto

import com.dnight.calinify.event.entity.EventDetailEntity
import com.dnight.calinify.event.entity.EventHistoryEntity
import com.dnight.calinify.event.entity.EventMainEntity

class EventHistoryDTO() {
    companion object {
        fun toEntity(eventMainEntity: EventMainEntity,
                     eventDetailEntity: EventDetailEntity): EventHistoryEntity {
            return EventHistoryEntity(
                event = eventMainEntity,

                summary = eventMainEntity.summary,
                startAt = eventMainEntity.startAt,
                endAt = eventMainEntity.endAt,
                priority = eventMainEntity.priority,
                repeatRule = eventMainEntity.repeatRule,

                description = eventDetailEntity.description,
                status = eventDetailEntity.status,
                transp = eventDetailEntity.transp,
                location = eventDetailEntity.location,
            )
        }
    }
}
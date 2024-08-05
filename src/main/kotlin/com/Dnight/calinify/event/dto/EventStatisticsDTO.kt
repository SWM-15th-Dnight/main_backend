package com.dnight.calinify.event.dto

import com.dnight.calinify.event.dto.request.EventCreateRequestDTO
import com.dnight.calinify.event.entity.EventStatisticsEntity

class EventStatisticsDTO {
    companion object{
        fun toEntity(eventMeta : EventCreateRequestDTO,
                     eventId : Long) : EventStatisticsEntity {
            return EventStatisticsEntity(
                eventStatisticsId = eventId,
                inputTypeId = eventMeta.inputTypeId,
                inputTimeTaken = eventMeta.inputTimeTaken,
            )
        }
    }
}
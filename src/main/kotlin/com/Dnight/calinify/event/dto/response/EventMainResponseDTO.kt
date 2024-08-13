package com.dnight.calinify.event.dto.response

import com.dnight.calinify.event.entity.EventMainEntity
import java.time.LocalDateTime

class EventMainResponseDTO(
    val eventId : Long,
    val summary : String,
    val startAt : LocalDateTime,
    val endAt : LocalDateTime,
    val repeatRule : String?,
    val priority : Int,

    // 1순위로 event entity 자체에 값이 있을 경우, 2순위로 event group 3순위 calendar
    val colorSetId : Int,
) {
    companion object {
        fun from(event : EventMainEntity) : EventMainResponseDTO {
            return EventMainResponseDTO(
                eventId = event.eventId!!,
                summary = event.summary,
                startAt = event.startAt,
                endAt = event.endAt,
                repeatRule = event.repeatRule,
                priority = event.priority,
                colorSetId = event.colorSetId!!
            )
        }
    }
}
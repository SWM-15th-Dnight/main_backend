package com.dnight.calinify.event.dto.request

import com.dnight.calinify.calendar.entity.CalendarEntity
import com.dnight.calinify.event.entity.EventEntity
import com.dnight.calinify.event.entity.EventStatus
import com.dnight.calinify.event.entity.EventTransp
import com.dnight.calinify.event.entity.EventUID
import com.fasterxml.jackson.annotation.JsonFormat
import jakarta.persistence.EnumType
import jakarta.persistence.Enumerated
import jakarta.validation.constraints.Min
import jakarta.validation.constraints.NotEmpty
import jakarta.validation.constraints.Size
import java.time.LocalDateTime

data class EventCreateRequestDTO(
    @field:NotEmpty
    val summary: String,

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss")
    val startAt: LocalDateTime,

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss")
    val endAt: LocalDateTime,

    @field:Size(max = 2047)
    val description : String?,

    @field:Size(min = 1, max= 9)
    val priority : Short = 5,

    @field:Size(max = 255)
    val location : String? = null,

    @field:Size(max = 255)
    val repeatRule : String? = null,

    @field:Min(1)
    val calendarId: Long,

    @field:Min(1)
    val eventGroupId : Long,

    @field:Min(1)
    val colorSetId : Int? = null,

    @Enumerated(EnumType.STRING)
    val status : EventStatus,

    @Enumerated(EnumType.STRING)
    val transp : EventTransp,

    val alarmId : Long? = null
) {
    companion object {
        fun toEntity(eventData : EventCreateRequestDTO,
                     calendar : CalendarEntity) : EventEntity {
            return EventEntity(
                uid = EventUID.genUID(),
                summary = eventData.summary,
                description = eventData.description,
                startAt = eventData.startAt,
                endAt = eventData.endAt,
                priority = eventData.priority,
                location = eventData.location,
                repeatRule = eventData.repeatRule,
                calendar = calendar,
                eventGroupId = eventData.eventGroupId,
                colorSetId = eventData.colorSetId,
                status = eventData.status,
                transp = eventData.transp,
                alarm = eventData.alarmId
            )
        }
    }
}
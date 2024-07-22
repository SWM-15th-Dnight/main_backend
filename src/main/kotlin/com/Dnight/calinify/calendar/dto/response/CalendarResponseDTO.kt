package com.dnight.calinify.calendar.dto.response

import com.dnight.calinify.calendar.entity.CalendarEntity
import java.time.LocalDateTime

data class CalendarResponseDTO(
    val calendarId : Long,
    val title : String,
    val timezoneId : String,
    val createdAt : LocalDateTime,
    val updatedAt : LocalDateTime,
    val colorSetId : Int,
    val description : String?,
) {
    companion object {
        fun from(calendar: CalendarEntity): CalendarResponseDTO {
            return CalendarResponseDTO(
                calendarId = calendar.calendarId,
                createdAt = calendar.createdAt,
                updatedAt = calendar.updatedAt,
                title = calendar.title,
                description = calendar.description,
                timezoneId = calendar.timezoneId,
                colorSetId = calendar.colorSetId,
            )
        }
    }
}
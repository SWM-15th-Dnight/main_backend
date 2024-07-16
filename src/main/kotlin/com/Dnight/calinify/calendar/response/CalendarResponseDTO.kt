package com.dnight.calinify.calendar.response

import com.dnight.calinify.calendar.entity.CalendarEntity
import com.dnight.calinify.calendar.entity.CalendarType
import java.time.LocalDateTime

data class CalendarResponseDTO(
    val calendarId : Long,
    val title : String,
    val timezoneId : String,
    val createdAt : LocalDateTime?,
    val updatedAt : LocalDateTime?,
    val calendarType : CalendarType,
    val colorId : Int,
    val description : String?,
) {
    companion object {
        fun from(calendar: CalendarEntity): CalendarResponseDTO {
            return CalendarResponseDTO(
                calendarId = calendar.calendarId!!,
                calendarType = calendar.calendarType,
                createdAt = calendar.createdAt,
                updatedAt = calendar.updatedAt,
                title = calendar.title,
                description = calendar.description,
                timezoneId = calendar.timezoneId,
                colorId = calendar.colorId,
            )
        }
    }
}
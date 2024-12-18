package com.dnight.calinify.calendar.dto.request

import com.dnight.calinify.calendar.entity.CalendarEntity
import com.dnight.calinify.user.entity.UserEntity
import jakarta.validation.constraints.Min
import jakarta.validation.constraints.NotBlank

class CalendarCreateDTO(

    @field:NotBlank
    val title: String,

    val description: String?,

    val timezone: String = "Asia/Seoul",

    @field:Min(1)
    val colorSetId: Int,
) {
    companion object {
        fun toEntity(calendarCreateDTO: CalendarCreateDTO, user: UserEntity) : CalendarEntity {
            return CalendarEntity(
                title = calendarCreateDTO.title,
                description = calendarCreateDTO.description,
                timezoneId = calendarCreateDTO.timezone,
                colorSetId = calendarCreateDTO.colorSetId,
                user = user
            )
        }
    }
}
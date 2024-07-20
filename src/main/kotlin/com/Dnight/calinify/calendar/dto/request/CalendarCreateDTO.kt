package com.dnight.calinify.calendar.dto.request

import com.dnight.calinify.calendar.entity.CalendarEntity
import com.dnight.calinify.calendar.entity.CalendarType
import com.dnight.calinify.user.entity.UserEntity
import jakarta.validation.constraints.Max
import jakarta.validation.constraints.Min
import jakarta.validation.constraints.NotBlank


data class CalendarCreateDTO(

    @field:Min(1, message = "필수 입력 값")
    val userId : Long,

    @field:NotBlank
    val title: String,

    val description: String?,

    val timezone: String = "Asia/Seoul",

    val calendarType: CalendarType,

    @field:Min(1) @field:Max(20)
    val colorId: Int,
) {
    companion object {
        fun from(calendar: CalendarCreateDTO, user: UserEntity) : CalendarEntity {
            return CalendarEntity(
                title = calendar.title,
                description = calendar.description,
                timezoneId = calendar.timezone,
                calendarType = calendar.calendarType,
                colorId = calendar.colorId,
                user = user
            )
        }
    }
}
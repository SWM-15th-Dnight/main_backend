package com.dnight.calinify.calendar.dto.request

import com.dnight.calinify.calendar.entity.CalendarEntity
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

    @field:Min(1)
    val colorSetId: Int,
) {
    companion object {
        fun toEntity(calendar: CalendarCreateDTO, user: UserEntity) : CalendarEntity {
            return CalendarEntity(
                title = calendar.title,
                description = calendar.description,
                timezoneId = calendar.timezone,
                colorSetId = calendar.colorSetId,
                user = user
            )
        }
    }
}
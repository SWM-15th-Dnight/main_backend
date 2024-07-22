package com.dnight.calinify.calendar.dto.request

import com.dnight.calinify.calendar.entity.CalendarEntity
import com.dnight.calinify.user.entity.UserEntity
import jakarta.validation.constraints.Min

data class CalendarUpdateDTO(
    @field:Min(1)
    val userId : Long,

    @field:Min(1)
    val calendarId : Long,

    val title : String,

    val description : String?,

    val timezoneId : String,

    var colorSetId : Int,
) {
    companion object{
        fun from(calendarUpdatedData: CalendarUpdateDTO, user: UserEntity) : CalendarEntity {
            return CalendarEntity(
                user = user,
                calendarId = calendarUpdatedData.calendarId,
                title = calendarUpdatedData.title,
                description = calendarUpdatedData.description,
                colorSetId = calendarUpdatedData.colorSetId,
                timezoneId = calendarUpdatedData.timezoneId
            )
        }
    }
}
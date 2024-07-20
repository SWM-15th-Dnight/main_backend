package com.dnight.calinify.calendar.dto.request

import com.dnight.calinify.calendar.entity.CalendarEntity
import com.dnight.calinify.calendar.entity.CalendarType
import com.dnight.calinify.user.entity.UserEntity
import jakarta.persistence.EnumType
import jakarta.persistence.Enumerated
import jakarta.validation.constraints.Min

data class CalendarUpdateDTO(
    @field:Min(1)
    val userId : Long,
    @field:Min(1)
    val calendarId : Long,

    val title : String,

    val description : String?,

    val timezoneId : String,

    @Enumerated(EnumType.STRING)
    val calendarType : CalendarType,

    var colorId : Int,
) {
    companion object{
        fun from(calendarUpdatedData: CalendarUpdateDTO, user: UserEntity) : CalendarEntity {
            return CalendarEntity(
                user = user,
                calendarId = calendarUpdatedData.calendarId,
                calendarType = calendarUpdatedData.calendarType,
                title = calendarUpdatedData.title,
                description = calendarUpdatedData.description,
                colorId = calendarUpdatedData.colorId,
                timezoneId = calendarUpdatedData.timezoneId
            )
        }
    }
}
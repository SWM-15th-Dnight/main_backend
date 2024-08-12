package com.dnight.calinify.calendar.dto.request

import jakarta.validation.constraints.Min
import jakarta.validation.constraints.NotEmpty

data class CalendarUpdateDTO(
    @field:Min(1)
    val calendarId : Long,

    @field:NotEmpty
    val title : String,

    val description : String?,

    val timezoneId : String = "Asia/Seoul",

    val colorSetId : Int,

    var isDeleted : Int
)
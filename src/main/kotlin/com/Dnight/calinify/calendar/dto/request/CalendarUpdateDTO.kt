package com.dnight.calinify.calendar.dto.request

import com.dnight.calinify.calendar.entity.CalendarEntity
import com.dnight.calinify.common.colorSets.ColorSetsEntity
import com.dnight.calinify.user.entity.UserEntity
import jakarta.validation.constraints.Min
import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.NotEmpty

data class CalendarUpdateDTO(
    @field:Min(1)
    val userId : Long,

    @field:Min(1)
    val calendarId : Long,

    @field:NotEmpty
    val title : String,

    val description : String?,

    val timezoneId : String = "Asia/Seoul",

    val colorSetId : Int,

    var deleted : Short
)
package com.dnight.calinify.calendar.request

import com.dnight.calinify.calendar.entity.CalendarType
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
)
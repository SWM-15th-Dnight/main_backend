package com.dnight.calinify.calendar.request

import com.dnight.calinify.calendar.entity.CalendarType
import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.NotEmpty
import jakarta.validation.constraints.NotNull

data class CalendarCreateDTO(
    @field:NotEmpty(message="유저 아이디 꼭 좀 넣어줄래?")
    val userId: Long,
    val title: String,
    val description: String?,
    val timezone: String = "Asia/Seoul",
    val calendarType: CalendarType,
    @field:NotNull
    val colorId: Int,
)
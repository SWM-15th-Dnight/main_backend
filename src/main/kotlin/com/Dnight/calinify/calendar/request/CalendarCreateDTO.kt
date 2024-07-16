package com.dnight.calinify.calendar.request

import com.dnight.calinify.calendar.entity.CalendarType

data class CalendarCreateDTO(
    val userId: Long,
    val title: String,
    val description: String,
    val timezone: String = "Asia/Seoul",
    val calendarType: CalendarType,
    val colorId: Int,
)
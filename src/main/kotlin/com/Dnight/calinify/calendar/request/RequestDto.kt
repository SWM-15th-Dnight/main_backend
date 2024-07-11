package com.dnight.calinify.calendar.request

import com.dnight.calinify.calendar.entity.CalendarType

data class CalendarDto(
    val timezoneId: String,
    val calendarType: CalendarType,
    val colorId: Int,
)
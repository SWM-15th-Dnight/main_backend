package com.dnight.calinify.calendar.repository

import com.dnight.calinify.calendar.entity.CalendarEntity
import org.springframework.data.jpa.repository.JpaRepository

interface CalendarRepository : JpaRepository<CalendarEntity, Long> {
    fun findByCalendarIdAndUserUserId(id: Long, user: Long) : CalendarEntity?
}
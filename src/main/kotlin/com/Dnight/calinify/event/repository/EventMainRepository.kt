package com.dnight.calinify.event.repository

import com.dnight.calinify.event.entity.EventMainEntity
import org.springframework.data.jpa.repository.JpaRepository

interface EventMainRepository : JpaRepository<EventMainEntity, Long> {
    fun findByEventIdAndCalendarUserUserId(eventId: Long, userId: Long) : EventMainEntity?
}
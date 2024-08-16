package com.dnight.calinify.event.repository

import com.dnight.calinify.event.entity.EventMainEntity
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param
import java.time.LocalDateTime

interface EventMainRepository : JpaRepository<EventMainEntity, Long> {
    fun findByEventIdAndCalendarUserUserId(eventId: Long, userId: Long) : EventMainEntity?

    @Query("SELECT e " +
            "FROM EventMainEntity e WHERE" +
            "((e.endAt >= :startMonth AND e.endAt <= :endMonth) OR" +
            "(e.startAt >= :startMonth AND e.startAt <= :endMonth))" +
            "AND e.calendar.user.userId = :userId AND e.isDeleted = 0")
    fun findUserEventBetween(@Param("startMonth") startMonth : LocalDateTime,
                             @Param("endMonth") endMonth : LocalDateTime,
                             @Param("userId") userId: Long): List<EventMainEntity>
}
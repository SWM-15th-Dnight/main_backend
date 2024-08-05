package com.dnight.calinify.event.repository

import com.dnight.calinify.event.entity.EventStatisticsEntity
import org.springframework.data.jpa.repository.JpaRepository

interface EventStatisticsRepository : JpaRepository<EventStatisticsEntity, Long>
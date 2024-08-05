package com.dnight.calinify.event.repository

import com.dnight.calinify.event.entity.EventHistoryEntity
import org.springframework.data.jpa.repository.JpaRepository

interface EventHistoryRepository : JpaRepository<EventHistoryEntity, Long>
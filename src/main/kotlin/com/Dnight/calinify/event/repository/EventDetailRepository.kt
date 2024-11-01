package com.dnight.calinify.event.repository

import com.dnight.calinify.event.entity.EventDetailEntity
import org.springframework.data.jpa.repository.JpaRepository

interface EventDetailRepository : JpaRepository<EventDetailEntity, Long> {
}
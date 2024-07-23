package com.dnight.calinify.event.repository

import com.dnight.calinify.event.entity.EventEntity
import org.springframework.data.jpa.repository.JpaRepository

interface EventRepository : JpaRepository<EventEntity, Long>
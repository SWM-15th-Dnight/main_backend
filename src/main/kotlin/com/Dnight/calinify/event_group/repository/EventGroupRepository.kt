package com.dnight.calinify.event_group.repository

import com.dnight.calinify.event_group.entity.EventGroupEntity
import org.springframework.data.jpa.repository.JpaRepository

interface EventGroupRepository : JpaRepository<EventGroupEntity, Long>
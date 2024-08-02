package com.dnight.calinify.ai_process.repository

import com.dnight.calinify.ai_process.entity.AiProcessingEventEntity
import org.springframework.data.jpa.repository.JpaRepository

interface AiProcessingEventRepository : JpaRepository<AiProcessingEventEntity, Long>
package com.dnight.calinify.ai_process.repository

import com.dnight.calinify.ai_process.entity.AiProcessingStatisticsEntity
import org.springframework.data.jpa.repository.JpaRepository

interface AiProcessingStatisticsRepository : JpaRepository<AiProcessingStatisticsEntity, Long>
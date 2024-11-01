package com.dnight.calinify.ai_process.entity

import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.Id
import jakarta.persistence.Table
import java.time.LocalDateTime

@Entity
@Table(name = "ai_processing_event")
class AiProcessingEventEntity(

    @Id
    val aiProcessingEventId : Long? = 0,

    @Column(nullable = false, length = 50)
    val summary : String,

    @Column(nullable = false)
    val startAt : LocalDateTime,

    @Column(nullable = false)
    val endAt : LocalDateTime,

    @Column(nullable = true, length = 2047)
    val description : String? = null,

    @Column(nullable = true, length = 255)
    val location : String? = null,

    @Column(nullable = true, length = 255)
    val repeatRule : String? = null
)
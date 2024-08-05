package com.dnight.calinify.event.entity

import jakarta.persistence.*
import org.springframework.data.annotation.LastModifiedDate
import java.time.LocalDateTime

@Entity
@Table(name = "event_history")
class EventHistoryEntity(
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    val eventHistoryId: Long? = 0,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "event_id", nullable = false)
    val event: EventEntity,

    @Column(nullable = false)
    val summary: String,

    @Column(nullable = false)
    val startAt : LocalDateTime,

    @Column(nullable = false)
    val endAt : LocalDateTime,

    @LastModifiedDate
    val updatedAt: LocalDateTime = LocalDateTime.now(),

    @Column(nullable = false, length = 2047)
    val description : String? = null,

    @Column(nullable = false)
    val priority: Int? = 5,

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    val status: EventStatus = EventStatus.TENTATIVE,

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    val transp: EventTransp = EventTransp.OPAQUE,

    @Column(nullable = false)
    val location : String? = null,

    @Column(nullable = false)
    val repeatRule : String? = null
)
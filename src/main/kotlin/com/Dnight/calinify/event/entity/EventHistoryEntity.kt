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
    val updatedAt: LocalDateTime,

    @Column(nullable = false, length = 2047)
    val description : String? = null,

    @Column(nullable = false)
    val priority: Short? = 5,

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
) {
    companion object {
        fun from(event : EventEntity) : EventHistoryEntity {
            return EventHistoryEntity(
                event = event,
                summary = event.summary,
                startAt = event.startAt,
                endAt = event.endAt,
                description = event.description,
                priority = event.priority,
                status = event.status,
                transp = event.transp,
                location = event.location,
                repeatRule = event.repeatRule,
                updatedAt = event.updatedAt
            )
        }
    }
}
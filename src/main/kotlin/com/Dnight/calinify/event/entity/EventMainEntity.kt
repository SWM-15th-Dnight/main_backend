package com.dnight.calinify.event.entity

import com.dnight.calinify.calendar.entity.CalendarEntity
import jakarta.persistence.*
import java.time.LocalDateTime

@Entity
@Table(name = "event_main")
class EventMainEntity(
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    val eventId : Long? = 0,

    @OneToOne(cascade = [CascadeType.ALL], fetch = FetchType.LAZY)
    @JoinColumn(name = "eventDetailId")
    val eventDetail: EventDetailEntity? = null,

    @JoinColumn(name = "calendarId")
    @ManyToOne(fetch = FetchType.LAZY)
    var calendar : CalendarEntity,

    @Column(nullable = false)
    var summary : String,

    @Column(nullable = false)
    var startAt : LocalDateTime,

    @Column(nullable = false)
    var endAt : LocalDateTime,

    @Column(nullable = false)
    var priority : Int = 5,

    @Column(nullable = true)
    var repeatRule : String? = null,

    @Column(nullable = false)
    var isDeleted : Int = 0,

    @Column(nullable = true)
    var colorSetId : Int? = null,
)
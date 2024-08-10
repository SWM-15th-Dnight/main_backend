package com.dnight.calinify.event.entity

import com.dnight.calinify.ai_process.entity.AiProcessingStatisticsEntity
import com.dnight.calinify.alarm.entity.AlarmEntity
import com.dnight.calinify.calendar.entity.CalendarEntity
import com.dnight.calinify.config.basicEntity.BasicEntity
import com.dnight.calinify.event_group.entity.EventGroupEntity
import jakarta.persistence.*
import java.time.LocalDateTime

@Entity
@Table(name = "event")
class EventEntity(

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    var eventId : Long? = 0,

    @Column(nullable = false, unique = true, length = 255)
    val uid : String,

    @Column(nullable = false, length = 50)
    var summary : String,

    @Column(nullable = false)
    var sequence : Int = 0,

    @Column(nullable = false)
    var startAt : LocalDateTime,

    @Column(nullable = false)
    var endAt : LocalDateTime,

    @Column(nullable = true, length = 2047)
    var description : String?,

    @Column(nullable = false)
    var priority : Int? = 5,

    @Column(nullable = true, length = 255)
    var location : String?,

    @Column(nullable = true, length = 255)
    var repeatRule : String? = null,

    @Column(nullable = false)
    var isDeleted : Int = 0,

    @JoinColumn(name = "calendarId")
    @ManyToOne(fetch = FetchType.LAZY)
    var calendar : CalendarEntity,

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    var status : EventStatus = EventStatus.TENTATIVE,

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    var transp : EventTransp = EventTransp.OPAQUE,

    @JoinColumn(name = "event_group_id")
    @ManyToOne(fetch = FetchType.LAZY)
    var eventGroup : EventGroupEntity? = null,

    @JoinColumn(name = "alarm_id")
    @OneToOne(fetch = FetchType.LAZY)
    var alarm : AlarmEntity? = null,

    // 아무리 생각해도 id만 넘겨주고, 색상 관련된 데이터는 FE가 갖고 있는 걸로
    @Column(nullable = true)
    var colorSetId : Int? = null,

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ai_processing_statistics_id")
    var aiProcessingStatistics: AiProcessingStatisticsEntity? = null,

    ) : BasicEntity()
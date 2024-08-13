package com.dnight.calinify.event.entity

import com.dnight.calinify.ai_process.entity.AiProcessingEventEntity
import com.dnight.calinify.alarm.entity.AlarmEntity
import com.dnight.calinify.config.basicEntity.BasicEntity
import com.dnight.calinify.event_group.entity.EventGroupEntity
import jakarta.persistence.*

@Entity
@Table(name = "event_detail")
class EventDetailEntity(

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    var eventDetailId : Long? = 0,

    @OneToOne(mappedBy = "eventDetail", fetch = FetchType.LAZY)
    val eventMain: EventMainEntity,

    @Column(nullable = false, unique = true, length = 255)
    val uid : String,

    @Column(nullable = false)
    var sequence : Int = 0,

    @Column(nullable = true, length = 2047)
    var description : String?,

    @Column(nullable = true, length = 255)
    var location : String?,

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

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ai_processing_event_id")
    var aiProcessingEvent: AiProcessingEventEntity? = null,

    ) : BasicEntity()
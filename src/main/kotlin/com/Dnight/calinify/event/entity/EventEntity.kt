package com.dnight.calinify.event.entity

import com.dnight.calinify.calendar.entity.CalendarEntity
import com.dnight.calinify.config.basicEntity.BasicEntity
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
    var priority : Short? = 5,

    @Column(nullable = true, length = 255)
    var location : String?,

    @Column(nullable = true, length = 255)
    var repeatRule : String? = null,

    @Column(nullable = false)
    var isDeleted : Short = 0,

    @JoinColumn(name = "calendarId")
    @ManyToOne(fetch = FetchType.LAZY)
    var calendar : CalendarEntity,

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    var status : EventStatus = EventStatus.TENTATIVE,

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    var transp : EventTransp = EventTransp.OPAQUE,

    // TODO 이하 속성들은 추후 Entity 생성 시, 조인 컬럼 설정
    @Column(nullable = true)
    var eventGroupId : Long? = null,

    @Column(nullable = true)
    var alarm : Long? = null,

    @Column(nullable = true)
    var colorSetId : Int? = null,

    ) : BasicEntity()
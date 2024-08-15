package com.dnight.calinify.event.entity

import com.dnight.calinify.calendar.entity.CalendarEntity
import jakarta.persistence.*
import java.time.LocalDateTime

@Entity
@Table(name = "event_main")
class EventMainEntity(
    /**
     * event Detail에 대해 비식별 관계이나,
     *
     * one to one 관계에서 lazy loading을 위한 프록시 객체 생성에 문제가 있어 이를 대체함.
     *
     * DB에 FK가 설정되어 있으며, 데이터 생성 시에 무조건 양 엔티티의 ID는 1:1로 묶임.
     *
     * 트랜잭션 설정과 로직 설계에 주의를 요함.
     *
     * @author 정인모
     */
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    val eventId : Long? = null,

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
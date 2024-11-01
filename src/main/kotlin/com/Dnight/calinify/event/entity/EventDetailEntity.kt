package com.dnight.calinify.event.entity

import com.dnight.calinify.ai_process.entity.AiProcessingEventEntity
import com.dnight.calinify.alarm.entity.AlarmEntity
import com.dnight.calinify.common.inputType.InputTypeEntity
import com.dnight.calinify.config.basicEntity.BasicEntity
import com.dnight.calinify.event_group.entity.EventGroupEntity
import jakarta.persistence.*

@Entity
@Table(name = "event_detail")
class EventDetailEntity(
    /**
     * event detail -> event main 1:1 참조 관계
     *
     * 역방향으로 1:1 참조 관계를 설정할 경우, lazy loading이 제대로 동작하지 않는 문제가 있음.
     *
     * 일정 기간 내의 일정을 가져올 경우, 적어도 수 십 개의 조회 쿼리가 날아갈 수 있어, 현행을 유지하고자 함.
     *
     * main이 detail을 참고 하고자 할 때는, 자신의 PK로 조회하면 됨.
     *
     * 따라서 main -> detail 참조가 제대로 이루어질 수 있도록 로직 설계에 주의를 요함.
     *
     * @see com.dnight.calinify.event.entity.EventMainEntity
     * @author 정인모
     */
    
    @Id
    @Column(name = "event_detail_id")
    val eventDetailId : Long,
    
    @MapsId(value = "eventDetailId")
    @OneToOne(targetEntity = EventMainEntity::class, fetch = FetchType.LAZY, cascade = [CascadeType.ALL])
    @JoinColumn(name = "eventDetailId", nullable = false)
    var eventMain : EventMainEntity,

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

    @JoinColumn(name = "input_type_id")
    @ManyToOne(fetch = FetchType.LAZY)
    var inputType : InputTypeEntity,

    @Column(name = "input_time_taken")
    var inputTimeTaken: Float,

    ) : BasicEntity()
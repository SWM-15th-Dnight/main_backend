package com.dnight.calinify.event.dto.request

import com.dnight.calinify.ai_process.entity.AiProcessingStatisticsEntity
import com.dnight.calinify.calendar.entity.CalendarEntity
import com.dnight.calinify.event.entity.EventEntity
import com.dnight.calinify.event.entity.EventStatus
import com.dnight.calinify.event.entity.EventTransp
import com.dnight.calinify.event.entity.EventUID
import com.fasterxml.jackson.annotation.JsonFormat
import jakarta.persistence.EnumType
import jakarta.persistence.Enumerated
import jakarta.validation.constraints.Min
import jakarta.validation.constraints.NotEmpty
import jakarta.validation.constraints.Size
import java.time.LocalDateTime

open class EventCreateRequestDTO(
    @field:NotEmpty
    val summary: String,

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss")
    val startAt: LocalDateTime,

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss")
    val endAt: LocalDateTime,

    @field:Size(max = 2047)
    val description : String?,

    @field:Size(min = 1, max= 9)
    val priority : Short = 5,

    @field:Size(max = 255)
    val location : String? = null,

    @field:Size(max = 255)
    val repeatRule : String? = null,

    @field:Min(1)
    val calendarId: Long,

    @field:Min(1)
    val eventGroupId : Long,

    @field:Min(1)
    val colorSetId : Int? = null,

    @Enumerated(EnumType.STRING)
    val status : EventStatus,

    @Enumerated(EnumType.STRING)
    val transp : EventTransp,

    val alarmId : Long? = null,

    @field:Min(1)
    val aiProcessingStatisticsId : Long? = null,
) {
    companion object {
        /**
         * aiProcessingStatisticsId가 존재하지 않는, Form으로 생성된 이벤트의 Entity를 만드는 메서드
         */
        fun toEntity(eventData : EventCreateRequestDTO,
                     calendar : CalendarEntity) : EventEntity {
            return EventEntity(
                uid = EventUID.genUID(),
                summary = eventData.summary,
                description = eventData.description,
                startAt = eventData.startAt,
                endAt = eventData.endAt,
                priority = eventData.priority,
                location = eventData.location,
                repeatRule = eventData.repeatRule,
                calendar = calendar,
                eventGroupId = eventData.eventGroupId,
                colorSetId = eventData.colorSetId,
                status = eventData.status,
                transp = eventData.transp,
                alarm = eventData.alarmId,
            )
        }

        /**
         * ai processing을 거쳐, aiProcessingStatisticsId를 함께 던져준 상태에서 적용.
         *
         * statistics 객체를 넣느냐 마느냐가 ai 답변인지 가르는 분기 플래그가 됨.
         */

        fun toEntity(eventData : EventCreateRequestDTO,
                     calendar : CalendarEntity,
                     aiProcessingStatisticsEntity: AiProcessingStatisticsEntity) : EventEntity {
            return EventEntity(
                uid = EventUID.genUID(),
                summary = eventData.summary,
                description = eventData.description,
                startAt = eventData.startAt,
                endAt = eventData.endAt,
                priority = eventData.priority,
                location = eventData.location,
                repeatRule = eventData.repeatRule,
                calendar = calendar,
                eventGroupId = eventData.eventGroupId,
                colorSetId = eventData.colorSetId,
                status = eventData.status,
                transp = eventData.transp,
                alarm = eventData.alarmId,
                aiProcessingStatistics = aiProcessingStatisticsEntity
            )
        }
    }
}
package com.dnight.calinify.event.dto.request

import com.dnight.calinify.ai_process.entity.AiProcessingStatisticsEntity
import com.dnight.calinify.alarm.dto.request.AlarmCreateRequestDTO
import com.dnight.calinify.alarm.entity.AlarmEntity
import com.dnight.calinify.calendar.entity.CalendarEntity
import com.dnight.calinify.event.entity.EventEntity
import com.dnight.calinify.event.entity.EventStatus
import com.dnight.calinify.event.entity.EventTransp
import com.dnight.calinify.event.entity.EventUID
import com.dnight.calinify.event_group.entity.EventGroupEntity
import com.fasterxml.jackson.annotation.JsonFormat
import io.swagger.v3.oas.annotations.media.Schema
import jakarta.persistence.EnumType
import jakarta.persistence.Enumerated
import jakarta.validation.constraints.Min
import jakarta.validation.constraints.NotEmpty
import jakarta.validation.constraints.Size
import org.springframework.format.annotation.DateTimeFormat
import java.time.LocalDateTime

class EventCreateRequestDTO(
    @field:NotEmpty
    @Schema(description = "일정의 제목, 요약", defaultValue = "저녁 식사")
    val summary: String,

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss")
    @Schema(description = "일정 시작 시간")
    val startAt: LocalDateTime,

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss")
    @Schema(description = "일정 종료 시간")
    val endAt: LocalDateTime,

    @field:Size(max = 2047)
    @Schema(description = "일정에 대한 부가 설명")
    val description : String?,

    @field:Size(min = 1, max= 9)
    @Schema(description = "일정의 중요도. 1-9이며 입력이 없으면 기본값 5", defaultValue = "5")
    val priority : Short = 5,

    @field:Size(max = 255)
    @Schema(description = "일정의 장소")
    val location : String? = null,

    @field:Size(max = 255)
    @Schema(description = "반복 조건, 별도의 표현식 참조")
    val repeatRule : String? = null,

    @Schema(description = "일정의 상태", defaultValue = "TENTATIVE")
    val status : EventStatus = EventStatus.TENTATIVE,

    @Schema(description = "일정의 투명도(시간의 중복 가능성)", defaultValue = "OPAQUE" )
    val transp : EventTransp = EventTransp.OPAQUE,

    @field:Min(1)
    @Schema(description = "일정이 입력될 캘린더 ID", defaultValue = "1")
    val calendarId: Long,

    @Schema(description = "일정의 컬러셋, 생략 가능", defaultValue = "1")
    val colorSetId : Int? = null,

    @Schema(description = "일정 그룹의 ID, 생략 가능")
    val eventGroupId : Long? = null,

    @Schema(description = "일정에 등록할 알람, 일정 생성 시 함께 생성")
    val alarm : AlarmCreateRequestDTO? = null,

    @Schema(description = "Ai Processing을 거친 일정일 경우 필수 포함. 순수 form 입력 시 공란")
    val processedEventId : Long? = null,

    @Schema(description = "사용자가 사용한 input 방식, 1:Form, 2:PlainText")
    @field:Min(1)
    val inputTypeId: Short,

    @field:Min(0)
    @Schema(description = "사용자가 일정을 등록하는 데에 활용한 종합 시간. 클라이언트에서 집계 후 전송")
    val inputTimeTaken : Float
) {
    companion object {
        fun toEntity(eventData : EventCreateRequestDTO,
                     calendar : CalendarEntity,
                     eventGroup : EventGroupEntity?,
                     alarm : AlarmEntity?,
                     aiProcessingStatisticsEntity: AiProcessingStatisticsEntity?) : EventEntity {
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
                eventGroup = eventGroup,
                colorSetId = eventData.colorSetId,
                status = eventData.status,
                transp = eventData.transp,
                alarm = alarm,
                aiProcessingStatistics = aiProcessingStatisticsEntity
            )
        }
    }
}
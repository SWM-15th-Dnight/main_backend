package com.dnight.calinify.event.dto.request

import com.dnight.calinify.event.entity.EventStatus
import com.dnight.calinify.event.entity.EventTransp
import com.fasterxml.jackson.annotation.JsonFormat
import io.swagger.v3.oas.annotations.media.Schema
import jakarta.validation.constraints.Max
import jakarta.validation.constraints.Min
import jakarta.validation.constraints.Size
import java.time.LocalDateTime

class EventUpdateRequestDTO(
    @field:Min(1)
    val eventId : Long,

    @field:Size(min=1, max=50)
    @Schema(description = "일정의 제목, 요약", defaultValue = "저녁 식사")
    val summary : String,

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss")
    val startAt : LocalDateTime,

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss")
    val endAt : LocalDateTime,

    @field:Size(max = 2047)
    @Schema(description = "일정에 대한 부가 설명")
    val description : String?,

    @field:Min(1)
    @field:Max(9)
    @Schema(description = "일정의 중요도. 1-9이며 입력이 없으면 기본값 5", defaultValue = "5")
    val priority : Int = 5,

    @field:Size(max = 255)
    @Schema(description = "일정의 장소")
    val location : String?,

    @field:Size(max = 255)
    @Schema(description = "반복 조건, 별도의 표현식 참조")
    val repeatRule : String?,

    @Schema(description = "일정의 상태", defaultValue = "TENTATIVE")
    val status : EventStatus,

    @Schema(description = "일정의 투명도(시간의 중복 가능성)", defaultValue = "OPAQUE" )
    val transp : EventTransp,

    @field:Min(1)
    @Schema(description = "일정이 입력될 캘린더 ID", defaultValue = "1")
    val calendarId: Long,

    @Schema(description = "일정의 컬러셋, 생략 가능", defaultValue = "1")
    val colorSetId : Int?,

    @Schema(description = "일정 그룹의 ID, 생략 가능")
    val eventGroupId : Long?,
    )
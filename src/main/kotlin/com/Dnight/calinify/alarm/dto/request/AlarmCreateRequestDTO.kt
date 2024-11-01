package com.dnight.calinify.alarm.dto.request

import com.dnight.calinify.alarm.entity.AlarmAction
import com.dnight.calinify.alarm.entity.AlarmEntity
import io.swagger.v3.oas.annotations.media.Schema
import jakarta.persistence.EnumType
import jakarta.persistence.Enumerated
import jakarta.validation.constraints.Size

class AlarmCreateRequestDTO(
    @field:Enumerated(EnumType.STRING)
    @Schema(description = "알람의 방식", defaultValue = "DISPLAY")
    val alarmAction: AlarmAction = AlarmAction.DISPLAY,

    @field:Size(max = 255)
    @Schema(description = "알람이 울릴 시간, 방식은 정해봐야할 것 같음", defaultValue = "B:15;")
    val alarmTrigger: String = "B15;",

    @field:Size(max = 50)
    @Schema(description = "알람과 함께 표시할 텍스트")
    val description : String,
) {
    companion object {
        fun toEntity(alarmCreateRequestDTO: AlarmCreateRequestDTO): AlarmEntity {
            return AlarmEntity(
                alarmTrigger = alarmCreateRequestDTO.alarmTrigger,
                description = alarmCreateRequestDTO.description,
                action = alarmCreateRequestDTO.alarmAction
            )
        }
    }
}
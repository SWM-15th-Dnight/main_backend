package com.dnight.calinify.alarm.dto.request

import com.dnight.calinify.alarm.entity.AlarmAction
import jakarta.persistence.EnumType
import jakarta.persistence.Enumerated
import jakarta.validation.constraints.Min
import jakarta.validation.constraints.Size

class AlarmUpdateRequestDTO(
    @field:Min(1)
    val alarmId: Long,

    @field:Enumerated(EnumType.STRING)
    val alarmAction: AlarmAction,

    @field:Size(max = 255)
    val alarmTrigger: String,

    @field:Size(max = 50)
    val description : String,
)
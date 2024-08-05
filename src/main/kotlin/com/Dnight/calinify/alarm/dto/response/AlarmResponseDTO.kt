package com.dnight.calinify.alarm.dto.response

import com.dnight.calinify.alarm.entity.AlarmAction
import com.dnight.calinify.alarm.entity.AlarmEntity

class AlarmResponseDTO(
    val alarmId: Long,

    val action: AlarmAction,

    val alarmTrigger: String,

    val description: String,
) {
    companion object {
        fun from(alarmEntity: AlarmEntity) : AlarmResponseDTO {
            return AlarmResponseDTO(
                alarmId = alarmEntity.alarmId!!,
                action = alarmEntity.action,
                alarmTrigger = alarmEntity.alarmTrigger,
                description = alarmEntity.description,
            )
        }
    }
}
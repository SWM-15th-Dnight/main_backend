package com.dnight.calinify.alarm.service

import com.dnight.calinify.alarm.dto.request.AlarmCreateRequestDTO
import com.dnight.calinify.alarm.dto.request.AlarmUpdateRequestDTO
import com.dnight.calinify.alarm.dto.response.AlarmResponseDTO
import com.dnight.calinify.alarm.repository.AlarmRepository
import com.dnight.calinify.config.basicResponse.ResponseCode
import com.dnight.calinify.config.exception.ClientException
import jakarta.transaction.Transactional
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service

@Service
class AlarmService(
    private val alarmRepository: AlarmRepository,
) {
    fun getAlarmById(alarmId : Long) : AlarmResponseDTO {

        val alarmEntity = alarmRepository.findByIdOrNull(alarmId) ?: throw ClientException(ResponseCode.NotFound)

        return AlarmResponseDTO.from(alarmEntity)
    }

    @Transactional
    fun createAlarm(alarmCreateRequestDTO: AlarmCreateRequestDTO) : Long {
        val alarmEntity = AlarmCreateRequestDTO.toEntity(alarmCreateRequestDTO)

        val createdAlarm = alarmRepository.save(alarmEntity)

        return createdAlarm.alarmId!!
    }

    @Transactional
    fun updateAlarm(alarmUpdateRequestDTO: AlarmUpdateRequestDTO) : AlarmResponseDTO {
        val alarmEntity = alarmRepository.findByIdOrNull(alarmUpdateRequestDTO.alarmId) ?: throw ClientException(ResponseCode.NotFound)

        alarmEntity.alarmTrigger = alarmUpdateRequestDTO.alarmTrigger
        alarmEntity.action = alarmUpdateRequestDTO.alarmAction
        alarmEntity.description = alarmUpdateRequestDTO.description

        return AlarmResponseDTO.from(alarmEntity)
    }

    @Transactional
    fun deleteAlarmById(alarmId : Long) : Map<String, Long> {
        alarmRepository.deleteById(alarmId)

        val deletedAlarmId = mapOf("deleted alarm id" to alarmId)

        return deletedAlarmId
    }
}
package com.dnight.calinify.alarm.controller

import com.dnight.calinify.alarm.dto.request.AlarmCreateRequestDTO
import com.dnight.calinify.alarm.dto.request.AlarmUpdateRequestDTO
import com.dnight.calinify.alarm.dto.response.AlarmResponseDTO
import com.dnight.calinify.alarm.service.AlarmService
import com.dnight.calinify.config.basicResponse.BasicResponse
import com.dnight.calinify.config.basicResponse.ResponseCode
import jakarta.validation.Valid
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/v1/alarm")
@Validated
class AlarmController(
    private val alarmService: AlarmService
) {
    // TODO User를 추가해 권한을 관리할 필요가 있다...
    @GetMapping("/{alarmId}")
    fun getAlarmById(@PathVariable alarmId : Long) : BasicResponse<AlarmResponseDTO>{
        val alarm = alarmService.getAlarmById(alarmId)

        return BasicResponse.ok(alarm, ResponseCode.ResponseSuccess)
    }

    @PostMapping("/")
    fun createAlarm(@Valid @RequestBody alarmCreateRequestDTO: AlarmCreateRequestDTO) : BasicResponse<AlarmResponseDTO>{
        val alarm = alarmService.createAlarm(alarmCreateRequestDTO)

        return BasicResponse.ok(alarm, ResponseCode.CreateSuccess)
    }

    @PutMapping("/")
    fun updateAlarm(@RequestBody alarmUpdateRequestDTO: AlarmUpdateRequestDTO) : BasicResponse<AlarmResponseDTO>{
        val alarm = alarmService.updateAlarm(alarmUpdateRequestDTO)

        return BasicResponse.ok(alarm, ResponseCode.UpdateSuccess)
    }

    @DeleteMapping("/{alarmId}")
    fun deleteAlarmById(@PathVariable alarmId : Long) : BasicResponse<Map<String, Long>>{
        val deletedAlarmMap = alarmService.deleteAlarmById(alarmId)

        return BasicResponse.ok(deletedAlarmMap, ResponseCode.DeleteSuccess)
    }
}
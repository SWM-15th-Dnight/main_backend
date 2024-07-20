package com.dnight.calinify.calendar.service

import com.dnight.calinify.calendar.entity.CalendarEntity
import com.dnight.calinify.calendar.repository.CalendarRepository
import com.dnight.calinify.calendar.dto.request.CalendarCreateDTO
import com.dnight.calinify.calendar.dto.request.CalendarUpdateDTO
import com.dnight.calinify.calendar.dto.response.CalendarResponseDTO
import com.dnight.calinify.config.basicResponse.ResponseCode
import com.dnight.calinify.config.exception.ClientException
import com.dnight.calinify.user.repository.UserRepository
import jakarta.transaction.Transactional
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service

@Service
class CalendarService(private val calendarRepository: CalendarRepository,
    private val userRepository: UserRepository) {

    fun getCalendarById(calendarId : Long) : CalendarResponseDTO {
        val calendar : CalendarEntity = calendarRepository.findByIdOrNull(calendarId) ?: throw ClientException(
            ResponseCode.NotFound)
        val calendarResponse = CalendarResponseDTO.from(calendar)

        return calendarResponse
    }

    @Transactional
    fun createCalendar(calendarData: CalendarCreateDTO): CalendarResponseDTO {
        val user = userRepository.findByIdOrNull(calendarData.userId) ?: throw ClientException(
            ResponseCode.UserNotFound)
        val calendar = CalendarCreateDTO.from(calendarData, user)
        val createdCalendar = calendarRepository.save(calendar)

        return CalendarResponseDTO.from(createdCalendar)
    }

    @Transactional
    fun updateCalendar(calendarUpdateData: CalendarUpdateDTO): CalendarResponseDTO {
        val user = userRepository.findByIdOrNull(calendarUpdateData.userId) ?: throw ClientException(
            ResponseCode.UserNotFound)
        println(calendarUpdateData)
        val calendar = calendarRepository.findById(calendarUpdateData.calendarId).get()
        println(calendar)

        if (calendar.user.userId != user.userId) throw ClientException(ResponseCode.NotYourResource)

        calendar.title = calendarUpdateData.title
        calendar.description = calendarUpdateData.description
        calendar.colorId = calendarUpdateData.colorId
        calendar.timezoneId = calendarUpdateData.timezoneId
        // ToDo 아직 update가 제대로 먹히고 있는 건지 확인이 어려움
        // ToDo 유저를 없애낫 파운드서 보내면, 유저 낫 파운드가 아닌, 리소스 가 발생
        // 필드의 맥스값을 넘거나, 양식에 안 맞는 값이 있으면 에러가 발생하나?

        return CalendarResponseDTO.from(calendar)
    }
}
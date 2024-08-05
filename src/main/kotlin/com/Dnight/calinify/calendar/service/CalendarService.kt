package com.dnight.calinify.calendar.service

import com.dnight.calinify.calendar.dto.request.CalendarCreateDTO
import com.dnight.calinify.calendar.dto.request.CalendarUpdateDTO
import com.dnight.calinify.calendar.dto.response.CalendarResponseDTO
import com.dnight.calinify.calendar.entity.CalendarEntity
import com.dnight.calinify.calendar.repository.CalendarRepository
import com.dnight.calinify.config.basicResponse.ResponseCode
import com.dnight.calinify.config.exception.ClientException
import com.dnight.calinify.user.repository.UserRepository
import jakarta.transaction.Transactional
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service

@Service
class CalendarService(
    private val calendarRepository: CalendarRepository,
    private val userRepository: UserRepository
) {
    @Transactional
    fun getCalendarById(calendarId : Long) : CalendarResponseDTO {

        val calendar : CalendarEntity = calendarRepository.findByIdOrNull(calendarId)
            ?: throw ClientException(ResponseCode.NotFound)

        // TODO 유저의 calendar 소유권한 확인 추가

        if (calendar.deleted.toInt() == 1) throw ClientException(ResponseCode.DeletedResource)

        val calendarResponse = CalendarResponseDTO.from(calendar)

        return calendarResponse
    }

    @Transactional
    fun createCalendar(calendarData: CalendarCreateDTO): CalendarResponseDTO {
        val user = userRepository.findByIdOrNull(calendarData.userId) ?: throw ClientException(
            ResponseCode.UserNotFound)
        val calendar = CalendarCreateDTO.toEntity(calendarData, user)
        val createdCalendar = calendarRepository.save(calendar)

        return CalendarResponseDTO.from(createdCalendar)
    }

    @Transactional
    fun updateCalendar(calendarUpdateData: CalendarUpdateDTO): CalendarResponseDTO {
        val user = userRepository.findByIdOrNull(calendarUpdateData.userId) ?: throw ClientException(
            ResponseCode.UserNotFound)

        val calendar = calendarRepository.findByIdOrNull(calendarUpdateData.calendarId) ?: throw ClientException(
            ResponseCode.NotFound
        )

        if (calendar.user.userId != user.userId) throw ClientException(ResponseCode.NotYourResource)

        calendar.title = calendarUpdateData.title
        calendar.description = calendarUpdateData.description
        calendar.colorSetId = calendarUpdateData.colorSetId
        calendar.timezoneId = calendarUpdateData.timezoneId
        calendar.deleted = calendarUpdateData.deleted

        return CalendarResponseDTO.from(calendar)
    }

    @Transactional
    fun deleteCalendarById(calendarId: Long, userId: Long): String {
        val calendar = calendarRepository.findByIdOrNull(calendarId) ?: throw ClientException(ResponseCode.NotFound)

        if (calendar.user.userId != userId) throw ClientException(ResponseCode.NotYourResource)

        try {
            calendar.deleted = 1
            return calendarId.toString()
        } catch (e: IllegalArgumentException) {
            throw ClientException(ResponseCode.NotFound)
        }
    }
}
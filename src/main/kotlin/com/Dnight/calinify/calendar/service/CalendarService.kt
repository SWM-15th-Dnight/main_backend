package com.dnight.calinify.calendar.service

import com.dnight.calinify.calendar.dto.request.CalendarCreateDTO
import com.dnight.calinify.calendar.dto.request.CalendarUpdateDTO
import com.dnight.calinify.calendar.dto.response.CalendarResponseDTO
import com.dnight.calinify.calendar.entity.CalendarEntity
import com.dnight.calinify.calendar.repository.CalendarRepository
import com.dnight.calinify.config.basicResponse.ResponseCode
import com.dnight.calinify.config.basicResponse.ResponseOk
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
    fun getCalendarById(calendarId : Long, userId : Long) : CalendarResponseDTO {

        val calendar : CalendarEntity = calendarRepository.findByCalendarIdAndUserUserId(calendarId, userId)
            ?: throw ClientException(ResponseCode.NotFound)

        if (calendar.isDeleted == 1) throw ClientException(ResponseCode.DeletedResource)
        val calendarResponse = CalendarResponseDTO.from(calendar)

        return calendarResponse
    }

    fun getAllCalendarByUserId(userId : Long) : List<CalendarResponseDTO> {

        val calendarList = calendarRepository.findAllByUserUserId(userId)

        val calendarListDTO = calendarList.map { calendar -> CalendarResponseDTO.from(calendar) }

        return calendarListDTO
    }

    @Transactional
    fun createCalendar(calendarData: CalendarCreateDTO, userId : Long): Long {
        val user = userRepository.findByIdOrNull(userId) ?: throw ClientException(ResponseCode.UserNotFound)
        val calendar = CalendarCreateDTO.toEntity(calendarData, user)
        val createdCalendar = calendarRepository.save(calendar)

        return createdCalendar.calendarId
    }

    @Transactional
    fun updateCalendar(calendarUpdateData: CalendarUpdateDTO, userId : Long): ResponseOk {

        val calendar = calendarRepository.findByCalendarIdAndUserUserId(calendarUpdateData.calendarId, userId) ?: throw ClientException(
            ResponseCode.NotFound
        )

        calendar.title = calendarUpdateData.title
        calendar.description = calendarUpdateData.description
        calendar.colorSetId = calendarUpdateData.colorSetId
        calendar.timezoneId = calendarUpdateData.timezoneId
        calendar.isDeleted = calendarUpdateData.isDeleted

        return ResponseOk()
    }

    @Transactional
    fun deleteCalendarById(calendarId: Long, userId: Long): ResponseOk {
        val calendar = calendarRepository.findByCalendarIdAndUserUserId(calendarId, userId)
            ?: throw ClientException(ResponseCode.NotFound)

        try {
            calendar.isDeleted = 1
            return ResponseOk()
        } catch (e: IllegalArgumentException) {
            throw ClientException(ResponseCode.NotFound)
        }
    }
}
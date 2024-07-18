package com.dnight.calinify.calendar.service

import com.dnight.calinify.calendar.entity.CalendarEntity
import com.dnight.calinify.calendar.repository.CalendarRepository
import com.dnight.calinify.calendar.request.CalendarCreateDTO
import com.dnight.calinify.calendar.response.CalendarResponseDTO
import com.dnight.calinify.config.basicResponse.ResponseCode
import com.dnight.calinify.config.exception.ClientException
import jakarta.transaction.Transactional
import org.springframework.stereotype.Service

@Service
class CalendarService(private val calendarRepository: CalendarRepository) {

    fun getCalendarById(calendarId : Long) : CalendarResponseDTO {
        val calendar : CalendarEntity = calendarRepository.findById(calendarId).orElse(null) ?: throw ClientException(
            ResponseCode.NotFound)
        val calendarResponse = CalendarResponseDTO.from(calendar)

        return calendarResponse
    }

    @Transactional
    fun createCalendar(calendarData: CalendarCreateDTO): CalendarResponseDTO {
        val calendar = CalendarEntity.from(calendarData)
        val createdCalendar = calendarRepository.save(calendar)

        return CalendarResponseDTO.from(createdCalendar)
    }
}
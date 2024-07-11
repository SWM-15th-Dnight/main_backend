package com.dnight.calinify.calendar.service

import com.dnight.calinify.calendar.entity.CalendarEntity
import com.dnight.calinify.calendar.repository.CalendarRepository
import com.dnight.calinify.calendar.response.CalendarResponseDTO
import com.dnight.calinify.config.customException.ResourceNotFound
import org.springframework.stereotype.Service

@Service
class CalendarService(private val calendarRepository: CalendarRepository) {

    fun getCalendarById(calendarId : Long) : CalendarResponseDTO {
        val calendar : CalendarEntity = calendarRepository.findById(calendarId).orElse(null) ?: throw ResourceNotFound()
        val calendarResponse = CalendarResponseDTO.from(calendar)

        return calendarResponse
    }
}
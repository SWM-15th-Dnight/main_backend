package com.dnight.calinify.event.service

import com.dnight.calinify.calendar.repository.CalendarRepository
import com.dnight.calinify.config.basicResponse.ResponseCode
import com.dnight.calinify.config.exception.ClientException
import com.dnight.calinify.event.dto.request.EventCreateRequestDTO
import com.dnight.calinify.event.dto.response.EventResponseDTO
import com.dnight.calinify.event.repository.EventRepository
import jakarta.transaction.Transactional
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service

@Service
class EventService(
    private val eventRepository: EventRepository,
    private val calendarRepository: CalendarRepository,
) {
    fun getEventById(eventId : Long) : EventResponseDTO {

        val event = eventRepository.findByIdOrNull(eventId) ?: throw ClientException(ResponseCode.NotFound)

        val eventResponseDTO = EventResponseDTO.from(event)

        return eventResponseDTO
    }

    @Transactional
    fun createEvent(eventCreateDTO: EventCreateRequestDTO) : EventResponseDTO {
        val calendarId : Long = eventCreateDTO.calendarId

        val calendarEntity = calendarRepository.findByIdOrNull(calendarId) ?: throw ClientException(ResponseCode.NotFound)

        val eventEntity = EventCreateRequestDTO.toEntity(eventCreateDTO, calendarEntity)

        try {
            eventRepository.save(eventEntity)
        } catch (ex: Exception) {
            throw ClientException(ResponseCode.DataSaveFailed)
        }

        val eventResponseDTO = EventResponseDTO.from(eventEntity)

        return eventResponseDTO
    }
}
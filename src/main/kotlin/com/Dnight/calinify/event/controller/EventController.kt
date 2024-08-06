package com.dnight.calinify.event.controller

import com.dnight.calinify.config.basicResponse.BasicResponse
import com.dnight.calinify.config.basicResponse.ResponseCode
import com.dnight.calinify.event.dto.request.EventCreateRequestDTO
import com.dnight.calinify.event.dto.request.EventUpdateRequestDTO
import com.dnight.calinify.event.dto.response.EventResponseDTO
import com.dnight.calinify.event.service.EventService
import jakarta.validation.Valid
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("api/v1/event")
@Validated
class EventController(
    val eventService: EventService
) {
    @GetMapping("/{eventId}")
    fun getEvent(@PathVariable eventId: Long): BasicResponse<EventResponseDTO> {
        val eventResponse: EventResponseDTO = eventService.getEventById(eventId)

        return BasicResponse.ok(eventResponse, ResponseCode.ResponseSuccess)
    }

    @PostMapping("/form")
    fun createFormEvent(@Valid @RequestBody eventCreateDTO: EventCreateRequestDTO): BasicResponse<EventResponseDTO> {
        val eventCreateResponse: EventResponseDTO = eventService.createEvent(eventCreateDTO)

        return BasicResponse.ok(eventCreateResponse, ResponseCode.CreateSuccess)
    }

    @PutMapping("/")
    fun updateEvent(@Valid @RequestBody eventUpdateRequestDTO: EventUpdateRequestDTO): BasicResponse<Long> {
        val eventId = eventService.updateEvent(eventUpdateRequestDTO)

        return BasicResponse.ok(eventId, ResponseCode.UpdateSuccess)
    }

    @DeleteMapping("/{eventId}")
    fun deleteEvent(@PathVariable eventId: Long): BasicResponse<Long> {
        val deletedEventID = eventService.deleteEvent(eventId)

        return BasicResponse.ok(deletedEventID, ResponseCode.DeleteSuccess)
    }
}
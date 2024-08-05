package com.dnight.calinify.event.controller

import com.dnight.calinify.config.basicResponse.BasicResponse
import com.dnight.calinify.config.basicResponse.ResponseCode
import com.dnight.calinify.event.dto.request.EventCreateRequestDTO
import com.dnight.calinify.event.dto.response.EventResponseDTO
import com.dnight.calinify.event.service.EventService
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("api/v1/event")
class EventController(
    val eventService: EventService
) {
    @GetMapping("/{eventId}")
    fun getEvent(@PathVariable eventId: Long) : BasicResponse<EventResponseDTO> {
        val eventResponse : EventResponseDTO = eventService.getEventById(eventId)

        return BasicResponse.ok(eventResponse, ResponseCode.ResponseSuccess)
    }

    @PostMapping("/form")
    fun createFormEvent(@RequestBody eventCreateDTO : EventCreateRequestDTO) : BasicResponse<EventResponseDTO> {
        val eventCreateResponse : EventResponseDTO = eventService.createEvent(eventCreateDTO)

        return BasicResponse.ok(eventCreateResponse, ResponseCode.CreateSuccess)
    }
}
package com.dnight.calinify.event.controller

import com.dnight.calinify.config.basicResponse.BasicResponse
import com.dnight.calinify.config.basicResponse.ResponseCode
import com.dnight.calinify.event.dto.request.EventCreateRequestDTO
import com.dnight.calinify.event.dto.response.EventResponseDTO
import com.dnight.calinify.event.entity.EventEntity
import com.dnight.calinify.event.service.EventService
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

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

    @PostMapping("/")
    fun createEvent(@RequestBody eventCreateDTO : EventCreateRequestDTO) {
        val eventCreateResponse : EventResponseDTO = eventService.createEvent(eventCreateDTO)

    }
}
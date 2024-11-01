package com.dnight.calinify.event.controller

import com.dnight.calinify.config.basicResponse.BasicResponse
import com.dnight.calinify.config.basicResponse.ResponseCode
import com.dnight.calinify.event.dto.request.EventCreateRequestDTO
import com.dnight.calinify.event.dto.request.EventUpdateRequestDTO
import com.dnight.calinify.event.dto.response.EventResponseDTO
import com.dnight.calinify.event.service.EventService
import jakarta.validation.Valid
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("api/v1/event")
@Validated
class EventController(
    val eventService: EventService
) {
    @GetMapping("/{eventId}")
    fun getEvent(@PathVariable eventId: Long,
                 @AuthenticationPrincipal userDetails: UserDetails): BasicResponse<EventResponseDTO> {
        val userId = userDetails.username.toLong()
        val eventResponse: EventResponseDTO = eventService.getEventById(eventId, userId)

        return BasicResponse.ok(eventResponse, ResponseCode.ResponseSuccess)
    }

    @PostMapping("/form")
    fun createFormEvent(@Valid @RequestBody eventCreateDTO: EventCreateRequestDTO,
                        @AuthenticationPrincipal userDetails: UserDetails,
                        ): BasicResponse<EventResponseDTO> {
        val userId = userDetails.username.toLong()
        val eventCreateResponse: EventResponseDTO = eventService.createEvent(eventCreateDTO, userId)

        return BasicResponse.ok(eventCreateResponse, ResponseCode.CreateSuccess)
    }

    @PutMapping("/")
    fun updateEvent(@Valid @RequestBody eventUpdateRequestDTO: EventUpdateRequestDTO,
                    @AuthenticationPrincipal userDetails: UserDetails): BasicResponse<Long> {
        val userId = userDetails.username.toLong()
        val eventId = eventService.updateEvent(eventUpdateRequestDTO, userId)

        return BasicResponse.ok(eventId, ResponseCode.UpdateSuccess)
    }

    @DeleteMapping("/{eventId}")
    fun deleteEvent(@PathVariable eventId: Long,
                    @AuthenticationPrincipal userDetails: UserDetails): BasicResponse<Long> {
        val userId = userDetails.username.toLong()
        val deletedEventID = eventService.deleteEvent(eventId, userId)

        return BasicResponse.ok(deletedEventID, ResponseCode.DeleteSuccess)
    }
}
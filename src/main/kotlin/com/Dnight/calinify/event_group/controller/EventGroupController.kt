package com.dnight.calinify.event_group.controller

import com.dnight.calinify.config.basicResponse.BasicResponse
import com.dnight.calinify.config.basicResponse.ResponseCode
import com.dnight.calinify.event_group.dto.request.EventGroupCreateRequestDTO
import com.dnight.calinify.event_group.dto.request.EventGroupUpdateRequestDTO
import com.dnight.calinify.event_group.dto.response.EventGroupResponseDTO
import com.dnight.calinify.event_group.service.EventGroupService
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/v1/eventGroup")
@Validated
class EventGroupController(
    private val eventGroupService: EventGroupService
) {
    @GetMapping("/{eventGroupId}")
    fun getEventGroupById(@PathVariable eventGroupId: Long) : BasicResponse<EventGroupResponseDTO> {
        val eventGroup = eventGroupService.getEventGroupById(eventGroupId)

        return BasicResponse.ok(eventGroup, ResponseCode.ResponseSuccess)
    }

    @PostMapping("/")
    fun createEventGroup(@RequestBody eventGroupCreateRequestDTO: EventGroupCreateRequestDTO)
    : BasicResponse<Long> {
        val eventGroup = eventGroupService.createEventGroup(eventGroupCreateRequestDTO)

        return BasicResponse.ok(eventGroup, ResponseCode.CreateSuccess)
    }

    @PutMapping("/")
    fun updateEventGroup(@RequestBody eventGroupUpdateRequestDTO: EventGroupUpdateRequestDTO)
    : BasicResponse<String> {
        val eventGroup = eventGroupService.updateEventGroup(eventGroupUpdateRequestDTO)

        return BasicResponse.ok("OK", ResponseCode.UpdateSuccess)
    }

    @DeleteMapping("/{eventGroupId}")
    fun deleteEventGroup(@PathVariable eventGroupId: Long) : BasicResponse<String> {
        eventGroupService.deleteEventGroup(eventGroupId)

        return BasicResponse.ok("OK", ResponseCode.DeleteSuccess)
    }
}
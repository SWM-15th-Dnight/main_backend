package com.dnight.calinify.event_group.controller

import com.dnight.calinify.config.basicResponse.BasicResponse
import com.dnight.calinify.config.basicResponse.ResponseCode
import com.dnight.calinify.config.basicResponse.ResponseOk
import com.dnight.calinify.event_group.dto.request.EventGroupCreateRequestDTO
import com.dnight.calinify.event_group.dto.request.EventGroupUpdateRequestDTO
import com.dnight.calinify.event_group.dto.response.EventGroupResponseDTO
import com.dnight.calinify.event_group.service.EventGroupService
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/v1/eventGroup")
@Validated
class EventGroupController(
    private val eventGroupService: EventGroupService
) {
    @GetMapping("/{eventGroupId}")
    fun getEventGroupById(@PathVariable eventGroupId: Long,
                          @AuthenticationPrincipal userDetails: UserDetails,
                          ) : BasicResponse<EventGroupResponseDTO> {
        val userId = userDetails.username.toLong()
        val eventGroup = eventGroupService.getEventGroupById(eventGroupId, userId)

        return BasicResponse.ok(eventGroup, ResponseCode.ResponseSuccess)
    }

    @PostMapping("/")
    fun createEventGroup(@RequestBody eventGroupCreateRequestDTO: EventGroupCreateRequestDTO,
                         @AuthenticationPrincipal userDetails: UserDetails,): BasicResponse<Long> {
        val userId = userDetails.username.toLong()
        val eventGroup = eventGroupService.createEventGroup(eventGroupCreateRequestDTO, userId)

        return BasicResponse.ok(eventGroup, ResponseCode.CreateSuccess)
    }

    @PutMapping("/")
    fun updateEventGroup(@RequestBody eventGroupUpdateRequestDTO: EventGroupUpdateRequestDTO,
                         @AuthenticationPrincipal userDetails: UserDetails,): BasicResponse<ResponseOk> {
        val userId = userDetails.username.toLong()
        val ok = eventGroupService.updateEventGroup(eventGroupUpdateRequestDTO, userId)

        return BasicResponse.ok(ok, ResponseCode.UpdateSuccess)
    }

    @DeleteMapping("/{eventGroupId}")
    fun deleteEventGroup(@PathVariable eventGroupId: Long,
                         @AuthenticationPrincipal userDetails: UserDetails) : BasicResponse<ResponseOk> {
        val userId = userDetails.username.toLong()
        val ok = eventGroupService.deleteEventGroup(eventGroupId, userId)

        return BasicResponse.ok(ok, ResponseCode.DeleteSuccess)
    }
}
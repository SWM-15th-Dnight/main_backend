package com.dnight.calinify.event.controller

import com.dnight.calinify.config.basicResponse.BasicResponse
import com.dnight.calinify.config.basicResponse.ResponseCode
import com.dnight.calinify.event.dto.response.EventMainResponseDTO
import com.dnight.calinify.event.service.EventListService
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController
import java.time.LocalDateTime


@RestController
@RequestMapping("/eventList")
@Validated
class EventListController(
    private val eventListService: EventListService
) {
    @GetMapping("/month")
    fun getMonthEventList(@RequestParam(required = true) year: Int,
                          @RequestParam(required = true) month: Int,
                          @AuthenticationPrincipal userDetails: UserDetails
                          ): BasicResponse<List<EventMainResponseDTO>> {
        val userId = userDetails.username.toLong()
        val eventList = eventListService.getMonthEventList(year, month, userId)

        return BasicResponse.ok(eventList, ResponseCode.ResponseSuccess)
    }

    @GetMapping("/week")
    fun getWeekEventList(@RequestParam(required = true) date : LocalDateTime,
                         @AuthenticationPrincipal userDetails: UserDetails,
                         ): BasicResponse<List<EventMainResponseDTO>> {
        val userId = userDetails.username.toLong()
        val eventList = eventListService.getWeekEventList(date, userId)

        return BasicResponse.ok(eventList, ResponseCode.ResponseSuccess)
    }
}
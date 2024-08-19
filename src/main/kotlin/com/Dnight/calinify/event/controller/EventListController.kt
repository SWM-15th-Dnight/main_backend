package com.dnight.calinify.event.controller

import com.dnight.calinify.config.basicResponse.BasicResponse
import com.dnight.calinify.config.basicResponse.ResponseCode
import com.dnight.calinify.config.exception.ClientException
import com.dnight.calinify.event.dto.response.EventMainResponseDTO
import com.dnight.calinify.event.service.EventListService
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.*
import java.time.LocalDateTime


@RestController
@RequestMapping("/eventList")
@Validated
class EventListController(
    private val eventListService: EventListService
) {
    @GetMapping("/all")
    fun getAllEvent(@AuthenticationPrincipal userDetails: UserDetails): BasicResponse<List<EventMainResponseDTO>> {
        val userId = userDetails.username.toLong()

        val eventList = eventListService.getAllEvent(userId)

        return BasicResponse.ok(eventList, ResponseCode.ResponseSuccess)
    }

    @GetMapping("/{calendarId}")
    fun getAllEventByCalendar(@PathVariable calendarId: Long,
                              @AuthenticationPrincipal userDetails: UserDetails) : BasicResponse<List<EventMainResponseDTO>> {
        val userId = userDetails.username.toLong()

        val eventList = eventListService.getAllEventByCalendar(calendarId, userId)

        return BasicResponse.ok(eventList, ResponseCode.ResponseSuccess)
    }

    @GetMapping("/monthEvent")
    fun getMonthEventList(@RequestParam(required = true) year: Int,
                          @RequestParam(required = true) month: Int,
                          @AuthenticationPrincipal userDetails: UserDetails
                          ): BasicResponse<List<EventMainResponseDTO>> {
        val userId = userDetails.username.toLong()
        val eventList = eventListService.getMonthEventList(year, month, userId)

        return BasicResponse.ok(eventList, ResponseCode.ResponseSuccess)
    }

    @GetMapping("/weekEvent")
    fun getWeekEventList(@RequestParam(required = true) date : LocalDateTime,
                         @AuthenticationPrincipal userDetails: UserDetails,
                         ): BasicResponse<List<EventMainResponseDTO>> {
        val userId = userDetails.username.toLong()
        val eventList = eventListService.getWeekEventList(date, userId)

        return BasicResponse.ok(eventList, ResponseCode.ResponseSuccess)
    }

    @GetMapping("/monthCalendar")
    fun getMonthEventListByCalendar(@RequestParam(required = true) year: Int,
                                    @RequestParam(required = true) month: Int,
                                    @RequestParam(required = true) calendarId : Long,
                                    @AuthenticationPrincipal userDetails: UserDetails,
                                    ): BasicResponse<List<EventMainResponseDTO>> {
        val userId = userDetails.username.toLong()
        val eventList = eventListService.getMonthEventListByCalendar(year, month, userId, calendarId)

        if (eventList.isEmpty()) throw ClientException(ResponseCode.NotFound, "calendar, month")

        return BasicResponse.ok(eventList, ResponseCode.ResponseSuccess)
    }

    @GetMapping("/weekCalendar")
    fun getWeekEventListByCalendar(@RequestParam(required = true) date : LocalDateTime,
                                   @RequestParam(required = true) calendarId : Long,
                                   @AuthenticationPrincipal userDetails: UserDetails,
    ): BasicResponse<List<EventMainResponseDTO>> {
        val userId = userDetails.username.toLong()
        val eventList = eventListService.getWeekEventListByCalendar(date, calendarId, userId)

        return BasicResponse.ok(eventList, ResponseCode.ResponseSuccess)
    }
}
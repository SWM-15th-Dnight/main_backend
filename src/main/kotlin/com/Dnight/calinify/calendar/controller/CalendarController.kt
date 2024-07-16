package com.dnight.calinify.calendar.controller

import com.dnight.calinify.calendar.request.CalendarCreateDTO
import com.dnight.calinify.calendar.response.CalendarResponseDTO
import com.dnight.calinify.calendar.service.CalendarService
import com.dnight.calinify.config.basicResponse.BasicResponse
import com.dnight.calinify.config.basicResponse.ResponseCode
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/v1/calendars")
class CalendarController(
    private val calendarService: CalendarService
) {
    @GetMapping("/{calendarId}")
    fun getCalendarById(@PathVariable calendarId: Long): BasicResponse<CalendarResponseDTO> {
        return BasicResponse.ok(calendarService.getCalendarById(calendarId), ResponseCode.ResponseSuccess)
    }

    @PostMapping("/")
    fun createCalendar(@RequestBody createCalendarDTO: CalendarCreateDTO): BasicResponse<CalendarResponseDTO> {
        val calendarResponse = calendarService.createCalendar(createCalendarDTO)
        return BasicResponse.ok(calendarResponse, ResponseCode.CreateSuccess)
    }
}
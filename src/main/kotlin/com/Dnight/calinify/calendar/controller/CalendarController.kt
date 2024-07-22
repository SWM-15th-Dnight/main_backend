package com.dnight.calinify.calendar.controller

import com.dnight.calinify.calendar.dto.request.CalendarCreateDTO
import com.dnight.calinify.calendar.dto.request.CalendarUpdateDTO
import com.dnight.calinify.calendar.dto.response.CalendarResponseDTO
import com.dnight.calinify.calendar.service.CalendarService
import com.dnight.calinify.config.basicResponse.BasicResponse
import com.dnight.calinify.config.basicResponse.ResponseCode
import jakarta.validation.Valid
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/v1/calendars")
@Validated
class CalendarController(
    private val calendarService: CalendarService
) {
    @GetMapping("/{calendarId}")
    fun getCalendarById(@PathVariable calendarId: Long): BasicResponse<CalendarResponseDTO> {
        return BasicResponse.ok(calendarService.getCalendarById(calendarId), ResponseCode.ResponseSuccess)
    }

    @PostMapping("/")
    fun createCalendar(@Valid @RequestBody createCalendarDTO: CalendarCreateDTO): BasicResponse<CalendarResponseDTO> {
        val calendarResponse = calendarService.createCalendar(createCalendarDTO)
        return BasicResponse.ok(calendarResponse, ResponseCode.CreateSuccess)
    }

    @PutMapping("/")
    fun updateCalendar(@Valid @RequestBody updateCalendarDTO: CalendarUpdateDTO): BasicResponse<CalendarResponseDTO> {
        val calendarResponse = calendarService.updateCalendar(updateCalendarDTO)
        return BasicResponse.ok(calendarResponse, ResponseCode.UpdateSuccess)
    }

    @DeleteMapping("/{calendarId}")
    fun deleteCalendar(@PathVariable calendarId: Long, @RequestParam userId: Long): BasicResponse<String> {
        val calendarDeleted = calendarService.deleteCalendarById(calendarId, userId)
        return BasicResponse.ok(calendarDeleted.toString(), ResponseCode.DeleteSuccess)

    }
}
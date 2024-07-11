package com.dnight.calinify.calendar.controller

import com.dnight.calinify.calendar.response.CalendarResponseDTO
import com.dnight.calinify.calendar.service.CalendarService
import com.dnight.calinify.config.basicResponse.BasicResponse
import com.dnight.calinify.config.basicResponse.ResponseCode
import com.dnight.calinify.config.customException.ResourceNotFound
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/calendar")
class CalendarController(
    private val calendarService: CalendarService
) {
    @GetMapping("/{calendarId}")
    fun getCalendarById(@PathVariable calendarId : Long) : ResponseEntity<Any> {
        return try {
            val calendarResponse = calendarService.getCalendarById(calendarId)
            ResponseEntity.ok().body(calendarResponse)
        } catch (error : ResourceNotFound) {
            ResponseEntity.status(404).body(error.message)
        }
    }
}
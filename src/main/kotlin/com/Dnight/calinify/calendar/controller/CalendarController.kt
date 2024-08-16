package com.dnight.calinify.calendar.controller

import com.dnight.calinify.calendar.dto.request.CalendarCreateDTO
import com.dnight.calinify.calendar.dto.request.CalendarUpdateDTO
import com.dnight.calinify.calendar.dto.response.CalendarResponseDTO
import com.dnight.calinify.calendar.service.CalendarService
import com.dnight.calinify.config.basicResponse.BasicResponse
import com.dnight.calinify.config.basicResponse.ResponseCode
import com.dnight.calinify.config.basicResponse.ResponseOk
import jakarta.validation.Valid
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/v1/calendars")
@Validated
class CalendarController(
    private val calendarService: CalendarService
) {
    @GetMapping("/{calendarId}")
    fun getCalendarById(@PathVariable calendarId: Long,
                        @AuthenticationPrincipal userDetails: UserDetails,
                        ): BasicResponse<CalendarResponseDTO> {
        val userId = userDetails.username.toLong()
        return BasicResponse.ok(calendarService.getCalendarById(calendarId, userId), ResponseCode.ResponseSuccess)
    }

    @GetMapping("/")
    fun getAllCalendarByUser(@AuthenticationPrincipal userDetails: UserDetails): BasicResponse<List<CalendarResponseDTO>> {
        val userId = userDetails.username.toLong()

        val calendarList = calendarService.getAllCalendarByUserId(userId)

        return BasicResponse.ok(calendarList, ResponseCode.ResponseSuccess)
    }

    @PostMapping("/")
    fun createCalendar(@Valid @RequestBody createCalendarDTO: CalendarCreateDTO,
                       @AuthenticationPrincipal userDetails: UserDetails,
                       ): BasicResponse<Long> {
        val userId = userDetails.username.toLong()
        val calendarResponse = calendarService.createCalendar(createCalendarDTO, userId)
        return BasicResponse.ok(calendarResponse, ResponseCode.CreateSuccess)
    }

    @PutMapping("/")
    fun updateCalendar(@Valid @RequestBody updateCalendarDTO: CalendarUpdateDTO,
                       @AuthenticationPrincipal userDetails: UserDetails,
                       ): BasicResponse<ResponseOk> {
        val userId = userDetails.username.toLong()
        val calendarResponse = calendarService.updateCalendar(updateCalendarDTO, userId)
        return BasicResponse.ok(calendarResponse, ResponseCode.UpdateSuccess)
    }

    @DeleteMapping("/{calendarId}")
    fun deleteCalendar(@PathVariable calendarId: Long,
                       @AuthenticationPrincipal userDetails: UserDetails,
                       ): BasicResponse<ResponseOk> {
        val userId = userDetails.username.toLong()
        val calendarDeleted = calendarService.deleteCalendarById(calendarId, userId)
        return BasicResponse.ok(calendarDeleted, ResponseCode.DeleteSuccess)
    }
}
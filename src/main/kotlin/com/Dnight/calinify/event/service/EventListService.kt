package com.dnight.calinify.event.service

import com.dnight.calinify.calendar.repository.CalendarRepository
import com.dnight.calinify.config.basicResponse.ResponseCode
import com.dnight.calinify.config.exception.ClientException
import com.dnight.calinify.event.dto.response.EventMainResponseDTO
import com.dnight.calinify.event.entity.EventMainEntity
import com.dnight.calinify.event.repository.EventMainRepository
import com.dnight.calinify.user.repository.UserRepository
import org.springframework.stereotype.Service
import java.time.DayOfWeek
import java.time.LocalDateTime
import java.time.temporal.TemporalAdjusters

@Service
class EventListService(
    private val calendarRepository: CalendarRepository,
    private val eventMainRepository: EventMainRepository,
    private val userRepository: UserRepository,
) {
    fun getAllEvent(userId : Long) : List<EventMainResponseDTO> {
        val calendarList = calendarRepository.findAllByUserUserId(userId)

        val eventList : MutableList<EventMainResponseDTO> = mutableListOf()

        // calendar의 삭제 여부 판별 후, event 개별 삭제 여부 판별
        for (calendar in calendarList) {
            if (calendar.isDeleted == 1) continue
            val calendarId : Long = calendar.calendarId
            eventMainRepository.findAllByCalendarCalendarId(calendar.calendarId)
                .forEach { if(it.isDeleted == 0) eventList.add(EventMainResponseDTO.from(it, calendarId)) }
        }

        return eventList
    }

    fun getAllEventByCalendar(calendarId: Long, userId: Long) : List<EventMainResponseDTO> {

        // calendar의 user검증
        val calendar = calendarRepository.findByCalendarIdAndUserUserId(id = calendarId, user = userId)
            ?: throw ClientException(ResponseCode.NotFoundOrNotMatchUser)

        if (calendar.isDeleted == 1) throw ClientException(ResponseCode.DeletedResource)

        val eventList = eventMainRepository.findAllByCalendarCalendarId(calendarId).mapNotNull {
            if (it.isDeleted == 0) EventMainResponseDTO.from(it, calendarId) else null}

        return eventList
    }

    fun getMonthEventList(year: Int, month: Int, userId: Long) : List<EventMainResponseDTO> {

        val startMonth : LocalDateTime = LocalDateTime.of(year, month, 1, 0, 0)
        val endMonth : LocalDateTime = LocalDateTime.of(year, month+1, 1, 0, 0).minusSeconds(1)

        val eventList : List<EventMainEntity> = eventMainRepository.findUserEventBetween(startMonth, endMonth, userId)
        // 위에서 userId, delete 판별
        val eventListDTO = eventList.map { EventMainResponseDTO.from(it, it.calendar.calendarId) }

        return eventListDTO
    }

    fun getWeekEventList(date : LocalDateTime, userId: Long) : List<EventMainResponseDTO> {

        val weekPair : Pair<LocalDateTime, LocalDateTime> = getWeekRange(date)

        val eventList : List<EventMainEntity> = eventMainRepository.findUserEventBetween(weekPair.first, weekPair.second, userId)
        // 위에서 userId, delete 판별
        val eventListDTO = eventList.map { EventMainResponseDTO.from(it, it.calendar.calendarId) }

        return eventListDTO
    }

    fun getMonthEventListByCalendar(year: Int, month: Int, userId: Long, calendarId: Long) : List<EventMainResponseDTO> {

        val startMonth : LocalDateTime = LocalDateTime.of(year, month, 1, 0, 0)
        val endMonth : LocalDateTime = LocalDateTime.of(year, month+1, 1, 0, 0).minusSeconds(1)

        val eventList : List<EventMainEntity> =
            eventMainRepository.findUserEventBetweenByCalendarCalendarId(startMonth, endMonth, userId, calendarId)
        // 위에서 userId, delete 판별
        val eventListDTO = eventList.map { EventMainResponseDTO.from(it, it.calendar.calendarId) }

        return eventListDTO
    }

    fun getWeekEventListByCalendar(date : LocalDateTime, calendarId: Long, userId: Long) : List<EventMainResponseDTO> {

        val weekPair : Pair<LocalDateTime, LocalDateTime> = getWeekRange(date)

        val eventList : List<EventMainEntity> =
            eventMainRepository.findUserEventBetweenByCalendarCalendarId(weekPair.first, weekPair.second, userId, calendarId)
        // 위에서 userId, delete 판별
        val eventListDTO = eventList.map { EventMainResponseDTO.from(it, it.calendar.calendarId) }

        return eventListDTO
    }


    fun getWeekRange(date: LocalDateTime): Pair<LocalDateTime, LocalDateTime> {
        // 주의 시작일을 월요일로 설정
        val startOfWeek = date.with(TemporalAdjusters.previousOrSame(DayOfWeek.SUNDAY))
        // 주의 종료일을 일요일로 설정
        val endOfWeek = date.with(TemporalAdjusters.nextOrSame(DayOfWeek.MONDAY))

        return Pair(startOfWeek, endOfWeek)
    }

}
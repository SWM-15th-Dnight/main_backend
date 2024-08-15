package com.dnight.calinify.event.service

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
    private val eventMainRepository: EventMainRepository,
    private val userRepository: UserRepository,
) {
    fun getMonthEventList(year: Int, month: Int, userId: Long) : List<EventMainResponseDTO> {

        val startMonth : LocalDateTime = LocalDateTime.of(year, month, 1, 0, 0)
        val endMonth : LocalDateTime = LocalDateTime.of(year, month+1, 1, 0, 0).minusSeconds(1)

        val eventList : List<EventMainEntity> = eventMainRepository.findUserEventBetween(startMonth, endMonth, userId)

        val eventListDTO = eventList.map { EventMainResponseDTO.from(it) }

        return eventListDTO
    }

    fun getWeekEventList(date : LocalDateTime, userId: Long) : List<EventMainResponseDTO> {

        val weekPair : Pair<LocalDateTime, LocalDateTime> = getWeekRange(date)

        val eventList : List<EventMainEntity> = eventMainRepository.findUserEventBetween(weekPair.first, weekPair.second, userId)

        val eventListDTO = eventList.map { EventMainResponseDTO.from(it) }

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
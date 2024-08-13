package com.dnight.calinify.event.service

import com.dnight.calinify.event.dto.response.EventMainResponseDTO
import com.dnight.calinify.event.entity.EventMainEntity
import com.dnight.calinify.event.repository.EventMainRepository
import com.dnight.calinify.user.repository.UserRepository
import org.springframework.stereotype.Service
import java.time.LocalDateTime

@Service
class EventListService(
    private val eventMainRepository: EventMainRepository,
    private val userRepository: UserRepository,
) {
    fun getMonthEventList(year: Int, month: Int, userId: Long) : List<EventMainResponseDTO> {
        val startMonth : LocalDateTime = LocalDateTime.of(year, month, 0, 0, 0)
        val endMonth : LocalDateTime = startMonth.minusSeconds(1)
        val eventList : List<EventMainEntity> = eventMainRepository.findUserEventBetween(startMonth, endMonth, userId)

        val eventListDTO = eventList.map { EventMainResponseDTO.from(it) }

        return eventListDTO
    }
}
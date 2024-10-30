package com.dnight.calinify.event.dto.response

import com.dnight.calinify.alarm.dto.response.AlarmResponseDTO
import com.dnight.calinify.event.entity.EventDetailEntity
import com.dnight.calinify.event.entity.EventMainEntity
import com.dnight.calinify.event.entity.EventStatus
import com.dnight.calinify.event.entity.EventTransp
import com.dnight.calinify.event_group.dto.response.EventGroupResponseDTO
import java.time.LocalDateTime

class EventResponseDTO(
    val eventId : Long,
    val calendarId : Long,
    val summary : String,
    val description : String? = null,
    val startAt : LocalDateTime,
    val endAt : LocalDateTime,
    val priority : Int?,
    val location : String? = null,
    val repeatRule : String? = null,
    val status : EventStatus,
    val transp : EventTransp,
    val eventGroup : EventGroupResponseDTO? = null,
    val alarm : AlarmResponseDTO? = null,
    val isAllday : Int = 0
){
    companion object {
        fun from(eventMain : EventMainEntity,
                 eventDetail: EventDetailEntity,) : EventResponseDTO {
            return EventResponseDTO(
                eventId = eventMain.eventId!!,
                calendarId = eventMain.calendar.calendarId,
                summary = eventMain.summary,
                startAt = eventMain.startAt,
                endAt = eventMain.endAt,
                priority = eventMain.priority,
                repeatRule = eventMain.repeatRule,
                isAllday = eventMain.isAllday,

                location = eventDetail.location,
                description = eventDetail.description,
                status = eventDetail.status,
                transp = eventDetail.transp,
                alarm = eventDetail.alarm?.let { AlarmResponseDTO.from(it) },
                eventGroup = eventDetail.eventGroup?.let { EventGroupResponseDTO.from(it) }
            )
        }
    }
}
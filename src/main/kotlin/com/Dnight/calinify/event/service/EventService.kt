package com.dnight.calinify.event.service

import com.dnight.calinify.ai_process.entity.AiProcessingStatisticsEntity
import com.dnight.calinify.ai_process.repository.AiProcessingStatisticsRepository
import com.dnight.calinify.alarm.dto.request.AlarmCreateRequestDTO
import com.dnight.calinify.alarm.entity.AlarmEntity
import com.dnight.calinify.alarm.repository.AlarmRepository
import com.dnight.calinify.alarm.service.AlarmService
import com.dnight.calinify.calendar.repository.CalendarRepository
import com.dnight.calinify.config.basicResponse.ResponseCode
import com.dnight.calinify.config.exception.ClientException
import com.dnight.calinify.event.dto.EventHistoryDTO
import com.dnight.calinify.event.dto.EventStatisticsDTO
import com.dnight.calinify.event.dto.request.EventCreateRequestDTO
import com.dnight.calinify.event.dto.request.EventUpdateRequestDTO
import com.dnight.calinify.event.dto.response.EventResponseDTO
import com.dnight.calinify.event.repository.EventHistoryRepository
import com.dnight.calinify.event.repository.EventRepository
import com.dnight.calinify.event.repository.EventStatisticsRepository
import com.dnight.calinify.event_group.entity.EventGroupEntity
import com.dnight.calinify.event_group.repository.EventGroupRepository
import jakarta.transaction.Transactional
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service

@Service
class EventService(
    private val eventRepository: EventRepository,
    private val eventGroupRepository: EventGroupRepository,
    private val alarmRepository: AlarmRepository,
    private val calendarRepository: CalendarRepository,
    private val eventHistoryRepository: EventHistoryRepository,
    private val aiProcessingStatisticsRepository: AiProcessingStatisticsRepository,
    private val eventStatisticsRepository: EventStatisticsRepository,

    private val alarmService: AlarmService,
) {
    fun getEventById(eventId : Long, userId : Long) : EventResponseDTO {

        val event = eventRepository.findByEventIdAndCalendarUserUserId(eventId, userId)
            ?: throw ClientException(ResponseCode.NotFound, "해당 유저의 일정이 아닐 수도 있음")

        if (event.isDeleted == 1) throw ClientException(ResponseCode.DeletedResource)

        return EventResponseDTO.from(event)
    }

    @Transactional
    fun createEvent(eventCreateDTO: EventCreateRequestDTO, userId : Long) : EventResponseDTO {

        //TODO 일정 입력 시, 바쁜 시간대에 추가로 일정이 있는지 없는지 체크하는 기능...
        //일정을 검색하는 방법에 대해 생각해보자...

        // get calendar
        val calendarEntity = calendarRepository.findByCalendarIdAndUserUserId(eventCreateDTO.calendarId, userId)
            ?: throw ClientException(ResponseCode.NotFound, "해당 유저의 캘린더가 아닐 수도 있음")

        if (calendarEntity.isDeleted == 1) throw ClientException(ResponseCode.DeletedResource, "calendar")

        // get event group - eventGroupId가 있을 경우 검색 후 값 집어넣기, 아니면 null 삽입
        var eventGroupEntity : EventGroupEntity? = null
        if (eventCreateDTO.eventGroupId is Long) {
            eventGroupEntity = eventGroupRepository.findByIdOrNull(eventCreateDTO.eventGroupId)
                ?: throw ClientException(ResponseCode.NotFound)
        }

        // alarm 생성 정보가 들어올 경우, 알람 생성도 진행, ID 반환받아와서 다시 검색하고 값 넣어줌.
        // alarm service에서 Entity를 넘길 수 없으니, 그냥 검색해서 다시 넣기.
        var alarmId : Long? = null
        if (eventCreateDTO.alarm is AlarmCreateRequestDTO) {
            alarmId = alarmService.createAlarm(eventCreateDTO.alarm)
        }

        val alarmEntity: AlarmEntity? = alarmId?. let {
            alarmRepository.findByIdOrNull(alarmId)
        }

        // ai statistics가 있으면 연결, 없으면 null
        var aiStatisticsEntity : AiProcessingStatisticsEntity? = null
        if (eventCreateDTO.processedEventId is Long) {
            aiStatisticsEntity = aiProcessingStatisticsRepository.findByIdOrNull(eventCreateDTO.processedEventId)
                ?: throw ClientException(ResponseCode.NotFound, "ai processing statistics id 값이 입력되었으나, 찾을 수 없음")
        }

        // event entity 저장
        var eventEntity = EventCreateRequestDTO.toEntity(eventCreateDTO, calendarEntity, eventGroupEntity, alarmEntity, aiStatisticsEntity)
        try {
            eventEntity = eventRepository.save(eventEntity)
        } catch (ex: Exception) {
            throw ClientException(ResponseCode.DataSaveFailed, "event 저장 실패")
        }

        val eventResponseDTO = EventResponseDTO.from(eventEntity)

        // History 추가
        val eventHistoryEntity = EventHistoryDTO.toEntity(eventEntity)
        try {
            eventHistoryRepository.save(eventHistoryEntity)
        } catch (ex: Exception) {
            throw ClientException(ResponseCode.DataSaveFailed, "event history 저장 실패")
        }

        // event statistics 추가
        val eventStatistics = EventStatisticsDTO.toEntity(eventCreateDTO, eventEntity.eventId!!)
        try {
            eventStatisticsRepository.save(eventStatistics)
        } catch (ex: Exception) {
            throw ClientException(ResponseCode.DataSaveFailed, "event statistics 저장 실패")
        }

        return eventResponseDTO
    }

    @Transactional
    fun updateEvent(eventUpdateDTO : EventUpdateRequestDTO, userId: Long) : Long {
        // 먼저, 해당 유저의 리소스가 맞는지 확인
        val eventEntity = eventRepository.findByEventIdAndCalendarUserUserId(eventUpdateDTO.eventId, userId)
            ?: throw ClientException(ResponseCode.NotFoundOrNotMatchUser, "event")

        // event group이 존재할 경우
        if (eventUpdateDTO.eventGroupId is Long) {
            val eventGroup = eventGroupRepository.findByIdOrNull(eventUpdateDTO.eventGroupId)
                ?: throw ClientException(ResponseCode.NotFoundOrNotMatchUser, "event Group")
        } else {
            eventEntity.eventGroup = null
        }

        // event calendar 변경
        eventEntity.calendar = calendarRepository.findByCalendarIdAndUserUserId(eventUpdateDTO.calendarId, userId)
            ?: throw ClientException(ResponseCode.NotFoundOrNotMatchUser, "calendar")

        // 필수값
        eventEntity.startAt = eventUpdateDTO.startAt
        eventEntity.endAt = eventUpdateDTO.endAt
        eventEntity.status = eventUpdateDTO.status
        eventEntity.transp = eventUpdateDTO.transp
        eventEntity.priority = eventUpdateDTO.priority

        // 선택값
        eventEntity.description = eventUpdateDTO.description
        eventEntity.location = eventUpdateDTO.location
        eventEntity.repeatRule = eventUpdateDTO.repeatRule
        eventEntity.colorSetId = eventUpdateDTO.colorSetId

        // 수정횟수 증가
        eventEntity.sequence += 1

        // History 등록
        val eventHistoryEntity = EventHistoryDTO.toEntity(eventEntity)

        try {
            eventHistoryRepository.save(eventHistoryEntity)
        } catch (ex : Exception) {
            throw ClientException(ResponseCode.DataSaveFailed, "event history")
        }

        return eventEntity.eventId!!
    }

    @Transactional
    fun deleteEvent(eventId : Long, userId: Long) : Long {
        // 실제 삭제는 배치 또는 값 검사를 통해?
        val event = eventRepository.findByEventIdAndCalendarUserUserId(eventId, userId)
            ?: throw ClientException(ResponseCode.NotFoundOrNotMatchUser)

        event.isDeleted = 1

        return eventId
    }
}
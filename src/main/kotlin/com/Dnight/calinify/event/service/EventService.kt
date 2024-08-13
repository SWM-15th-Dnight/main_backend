package com.dnight.calinify.event.service

import com.dnight.calinify.ai_process.entity.AiProcessingEventEntity
import com.dnight.calinify.ai_process.repository.AiProcessingEventRepository
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
import com.dnight.calinify.event.repository.EventDetailRepository
import com.dnight.calinify.event.repository.EventHistoryRepository
import com.dnight.calinify.event.repository.EventMainRepository
import com.dnight.calinify.event.repository.EventStatisticsRepository
import com.dnight.calinify.event_group.entity.EventGroupEntity
import com.dnight.calinify.event_group.repository.EventGroupRepository
import jakarta.transaction.Transactional
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service

@Service
class EventService(
    private val eventMainRepository: EventMainRepository,
    private val eventDetailRepository: EventDetailRepository,
    private val eventGroupRepository: EventGroupRepository,
    private val alarmRepository: AlarmRepository,
    private val calendarRepository: CalendarRepository,
    private val eventHistoryRepository: EventHistoryRepository,
    private val aiProcessingStatisticsRepository: AiProcessingStatisticsRepository,
    private val eventStatisticsRepository: EventStatisticsRepository,

    private val alarmService: AlarmService,
    private val aiProcessingEventRepository: AiProcessingEventRepository,
) {
    fun getEventById(eventId : Long, userId : Long) : EventResponseDTO {
        /**
         * 사용자에게 필요한 일정과 관련된 데이터를 반환한다.
         *
         * main + detail
         *
         * color set id의 순위는 1. event 2. group 3. calendar
         */
        val eventMain = eventMainRepository.findByEventIdAndCalendarUserUserId(eventId, userId)
            ?: throw ClientException(ResponseCode.NotFoundOrNotMatchUser, "event")

        if (eventMain.isDeleted == 1) throw ClientException(ResponseCode.DeletedResource)

        val eventDetail = eventDetailRepository.findByIdOrNull(eventId)!!

        return EventResponseDTO.from(eventMain, eventDetail)
    }

    @Transactional
    fun createEvent(eventCreateDTO: EventCreateRequestDTO, userId : Long) : EventResponseDTO {

        //TODO 일정 입력 시, 바쁜 시간대에 추가로 일정이 있는지 없는지 체크하는 기능...
        //일정을 검색하는 방법에 대해 생각해보자...

        // get calendar
        val calendarEntity = calendarRepository.findByCalendarIdAndUserUserId(eventCreateDTO.calendarId, userId)
            ?: throw ClientException(ResponseCode.NotFoundOrNotMatchUser, "calendar")

        if (calendarEntity.isDeleted == 1) throw ClientException(ResponseCode.DeletedResource, "calendar")

        // get event group - eventGroupId가 있을 경우 검색 후 값 집어넣기, 아니면 null 삽입
        var eventGroupEntity : EventGroupEntity? = null
        if (eventCreateDTO.eventGroupId is Long) {
            eventGroupEntity = eventGroupRepository.findByIdOrNull(eventCreateDTO.eventGroupId)
                ?: throw ClientException(ResponseCode.NotFound, "eventGroup")
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

        // ai processing event 있으면 연결, 없으면 null
        var aiProcessingEventEntity : AiProcessingEventEntity? = null
        if (eventCreateDTO.processedEventId is Long) {
            aiProcessingEventEntity = aiProcessingEventRepository.findByIdOrNull(eventCreateDTO.processedEventId)
                ?: throw ClientException(ResponseCode.NotFound, "ai processing event")
        }

        // event main entity 저장
        var eventMainEntity = EventCreateRequestDTO.toMainEntity(eventCreateDTO, calendarEntity)
        try {
            eventMainEntity = eventMainRepository.save(eventMainEntity)
        } catch (ex: Exception) {
            throw ClientException(ResponseCode.DataSaveFailed, "event main")
        }

        // event detail entity 저장
        var eventDetailEntity = EventCreateRequestDTO.toDetailEntity(
            eventMainEntity, eventCreateDTO, eventGroupEntity, alarmEntity, aiProcessingEventEntity)
        try {
            eventDetailEntity = eventDetailRepository.save(eventDetailEntity)
        } catch (ex: Exception) {
            throw ClientException(ResponseCode.DataSaveFailed, "event detail")
        }

        val eventResponseDTO = EventResponseDTO.from(eventMainEntity, eventDetailEntity)

        // History 추가
        val eventHistoryEntity = EventHistoryDTO.toEntity(eventMainEntity, eventDetailEntity)
        try {
            eventHistoryRepository.save(eventHistoryEntity)
        } catch (ex: Exception) {
            throw ClientException(ResponseCode.DataSaveFailed, "event history")
        }

        // event statistics 추가
        val eventStatistics = EventStatisticsDTO.toEntity(eventCreateDTO, eventMainEntity.eventId!!)
        try {
            eventStatisticsRepository.save(eventStatistics)
        } catch (ex: Exception) {
            throw ClientException(ResponseCode.DataSaveFailed, "event statistics")
        }

        return eventResponseDTO
    }

    @Transactional
    fun updateEvent(eventUpdateDTO : EventUpdateRequestDTO, userId: Long) : Long {
        // 먼저, 해당 유저의 리소스가 맞는지 확인
        val eventMainEntity = eventMainRepository.findByEventIdAndCalendarUserUserId(eventUpdateDTO.eventId, userId)
            ?: throw ClientException(ResponseCode.NotFoundOrNotMatchUser, "event")

        val eventDetailEntity = eventDetailRepository.findByIdOrNull(eventUpdateDTO.eventId)!!

        // event group이 존재할 경우
        if (eventUpdateDTO.eventGroupId is Long) {
            val eventGroup = eventGroupRepository.findByIdOrNull(eventUpdateDTO.eventGroupId)
                ?: throw ClientException(ResponseCode.NotFoundOrNotMatchUser, "event Group")
            eventDetailEntity.eventGroup = eventGroup
        } else {
            eventDetailEntity.eventGroup = null
        }

        // event calendar 변경
        eventMainEntity.calendar = calendarRepository.findByCalendarIdAndUserUserId(eventUpdateDTO.calendarId, userId)
            ?: throw ClientException(ResponseCode.NotFoundOrNotMatchUser, "calendar")

        // 필수값
        eventMainEntity.startAt = eventUpdateDTO.startAt
        eventMainEntity.endAt = eventUpdateDTO.endAt
        eventDetailEntity.status = eventUpdateDTO.status
        eventDetailEntity.transp = eventUpdateDTO.transp
        eventMainEntity.priority = eventUpdateDTO.priority

        // 선택값
        eventDetailEntity.description = eventUpdateDTO.description
        eventDetailEntity.location = eventUpdateDTO.location
        eventMainEntity.repeatRule = eventUpdateDTO.repeatRule
        eventMainEntity.colorSetId = eventUpdateDTO.colorSetId

        // 수정횟수 증가
        eventDetailEntity.sequence += 1

        // History 등록
        val eventHistoryEntity = EventHistoryDTO.toEntity(eventMainEntity, eventDetailEntity)

        try {
            eventHistoryRepository.save(eventHistoryEntity)
        } catch (ex : Exception) {
            throw ClientException(ResponseCode.DataSaveFailed, "event history")
        }

        return eventMainEntity.eventId!!
    }

    @Transactional
    fun deleteEvent(eventId : Long, userId: Long) : Long {
        // 실제 삭제는 배치 또는 값 검사를 통해?
        val event = eventMainRepository.findByEventIdAndCalendarUserUserId(eventId, userId)
            ?: throw ClientException(ResponseCode.NotFoundOrNotMatchUser)

        event.isDeleted = 1

        return eventId
    }
}
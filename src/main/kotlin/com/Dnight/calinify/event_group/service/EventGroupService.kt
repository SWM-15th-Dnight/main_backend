package com.dnight.calinify.event_group.service

import com.dnight.calinify.common.colorSets.ColorSetsRepository
import com.dnight.calinify.config.basicResponse.ResponseCode
import com.dnight.calinify.config.exception.ClientException
import com.dnight.calinify.event_group.dto.request.EventGroupCreateRequestDTO
import com.dnight.calinify.event_group.dto.request.EventGroupUpdateRequestDTO
import com.dnight.calinify.event_group.dto.response.EventGroupResponseDTO
import com.dnight.calinify.event_group.repository.EventGroupRepository
import com.dnight.calinify.user.repository.UserRepository
import jakarta.transaction.Transactional
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service

@Service
class EventGroupService(
    private val eventGroupRepository: EventGroupRepository,
    private val userRepository: UserRepository,
    private val colorSetsRepository: ColorSetsRepository,
) {

    fun getEventGroupById(eventGroupId : Long) : EventGroupResponseDTO {
        val eventGroupEntity = eventGroupRepository.findByIdOrNull(eventGroupId)
            ?: throw ClientException(ResponseCode.NotFound)

        return EventGroupResponseDTO.from(eventGroupEntity)
    }

    @Transactional
    fun createEventGroup(eventGroupCreateRequestDTO: EventGroupCreateRequestDTO) : Long {

        // TODO user logic 구현
        val user = userRepository.findByIdOrNull(1) ?: throw ClientException(ResponseCode.UserNotFound)

        val colorSet = colorSetsRepository.findByIdOrNull(eventGroupCreateRequestDTO.colorSetId)
            ?: throw ClientException(ResponseCode.NotFound, "color set Id")

        val eventGroupEntity = EventGroupCreateRequestDTO.toEntity(eventGroupCreateRequestDTO, user, colorSet)

        val eventGroup = eventGroupRepository.save(eventGroupEntity)

        return eventGroup.groupId!!
    }

    @Transactional
    fun updateEventGroup(eventGroupUpdateRequestDTO: EventGroupUpdateRequestDTO) {

        val eventGroupEntity = eventGroupRepository.findByIdOrNull(eventGroupUpdateRequestDTO.groupId)
            ?: throw ClientException(ResponseCode.NotFound, "group Id")

        val colorSet = colorSetsRepository.findByIdOrNull(eventGroupUpdateRequestDTO.colorSetId)
            ?: throw ClientException(ResponseCode.NotFound, "color set Id")

        eventGroupEntity.colorSet = colorSet
        eventGroupEntity.groupName = eventGroupUpdateRequestDTO.groupName
        eventGroupEntity.description = eventGroupUpdateRequestDTO.description
    }

    @Transactional
    fun deleteEventGroup(eventGroupId: Long) {

        val eventGroupEntity = eventGroupRepository.findByIdOrNull(eventGroupId) ?: throw ClientException(ResponseCode.NotFound)

        eventGroupRepository.delete(eventGroupEntity)
    }
}
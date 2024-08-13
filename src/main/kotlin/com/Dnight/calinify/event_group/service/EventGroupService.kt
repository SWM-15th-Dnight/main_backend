package com.dnight.calinify.event_group.service

import com.dnight.calinify.common.colorSet.ColorSetRepository
import com.dnight.calinify.config.basicResponse.ResponseCode
import com.dnight.calinify.config.basicResponse.ResponseOk
import com.dnight.calinify.config.exception.ClientException
import com.dnight.calinify.event_group.dto.request.EventGroupCreateRequestDTO
import com.dnight.calinify.event_group.dto.request.EventGroupUpdateRequestDTO
import com.dnight.calinify.event_group.dto.response.EventGroupResponseDTO
import com.dnight.calinify.event_group.repository.EventGroupRepository
import com.dnight.calinify.event_group.repository.GroupCategoryRepository
import com.dnight.calinify.user.repository.UserRepository
import jakarta.transaction.Transactional
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service

@Service
class EventGroupService(
    private val eventGroupRepository: EventGroupRepository,
    private val userRepository: UserRepository,
    private val colorSetRepository: ColorSetRepository,
    private val groupCategoryRepository: GroupCategoryRepository,
) {

    fun getEventGroupById(eventGroupId : Long, userId: Long) : EventGroupResponseDTO {
        val eventGroupEntity = eventGroupRepository.findByIdOrNull(eventGroupId)
            ?: throw ClientException(ResponseCode.NotFound)

        if (eventGroupEntity.user.userId != userId) throw ClientException(ResponseCode.NotYourResource)

        return EventGroupResponseDTO.from(eventGroupEntity)
    }

    @Transactional
    fun createEventGroup(eventGroupCreateRequestDTO: EventGroupCreateRequestDTO, userId: Long) : Long {

        val user = userRepository.findByIdOrNull(userId) ?: throw ClientException(ResponseCode.UserNotFound)

        val colorSet = colorSetRepository.findByIdOrNull(eventGroupCreateRequestDTO.colorSetId)
            ?: throw ClientException(ResponseCode.NotFound, "color set Id")

        val groupCategoryEntity = groupCategoryRepository.findByIdOrNull(eventGroupCreateRequestDTO.groupCategoryId)
            ?: throw ClientException(ResponseCode.NotFound, "group category Id")

        val eventGroupEntity = EventGroupCreateRequestDTO.toEntity(
            eventGroupCreateRequestDTO, user, colorSet, groupCategoryEntity)

        val eventGroup = eventGroupRepository.save(eventGroupEntity)

        return eventGroup.groupId!!
    }

    @Transactional
    fun updateEventGroup(eventGroupUpdateRequestDTO: EventGroupUpdateRequestDTO, userId: Long) : ResponseOk {

        val eventGroupEntity = eventGroupRepository.findByIdOrNull(eventGroupUpdateRequestDTO.groupId)
            ?: throw ClientException(ResponseCode.NotFound, "group Id")

        if (eventGroupEntity.user.userId != userId) throw ClientException(ResponseCode.NotYourResource)

        val colorSet = colorSetRepository.findByIdOrNull(eventGroupUpdateRequestDTO.colorSetId)
            ?: throw ClientException(ResponseCode.NotFound, "color set Id")

        eventGroupEntity.colorSet = colorSet
        eventGroupEntity.groupName = eventGroupUpdateRequestDTO.groupName
        eventGroupEntity.description = eventGroupUpdateRequestDTO.description

        return ResponseOk()
    }

    @Transactional
    fun deleteEventGroup(eventGroupId: Long, userId: Long) : ResponseOk {

        val eventGroupEntity = eventGroupRepository.findByIdOrNull(eventGroupId) ?: throw ClientException(ResponseCode.NotFound)

        if (eventGroupEntity.user.userId != userId) throw ClientException(ResponseCode.NotYourResource)

        eventGroupRepository.delete(eventGroupEntity)

        return ResponseOk()
    }
}
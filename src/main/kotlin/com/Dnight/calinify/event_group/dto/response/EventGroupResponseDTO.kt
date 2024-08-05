package com.dnight.calinify.event_group.dto.response

import com.dnight.calinify.event_group.entity.EventGroupEntity

class EventGroupResponseDTO(
    val groupName: String,

    val description: String? = null,

    val colorSetId: Int? = null,
) {
    companion object {
        fun from(groupEntity: EventGroupEntity) : EventGroupResponseDTO {
            return EventGroupResponseDTO(
                groupName = groupEntity.groupName,
                description = groupEntity.description,
                colorSetId = groupEntity.colorSet.colorSetId,
            )
        }
    }
}
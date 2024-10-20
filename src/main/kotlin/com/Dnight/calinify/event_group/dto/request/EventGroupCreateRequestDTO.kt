package com.dnight.calinify.event_group.dto.request

import com.dnight.calinify.event_group.entity.EventGroupEntity
import com.dnight.calinify.event_group.entity.GroupCategoryEntity
import com.dnight.calinify.user.entity.UserEntity
import jakarta.validation.constraints.Min
import jakarta.validation.constraints.Size

class EventGroupCreateRequestDTO (
    @field:Size(min = 1, max = 20)
    val groupName: String,

    @field:Size(max = 255)
    val description: String? = null,

    @field:Min(1)
    val colorSetId: Int,

    @field:Min(1)
    val groupCategoryId : Int,
) {
    companion object {
        fun toEntity(eventGroupCreateRequestDTO: EventGroupCreateRequestDTO,
                     user: UserEntity,
                     colorSetId: Int,
                     groupCategory: GroupCategoryEntity) : EventGroupEntity {
            return EventGroupEntity(
                groupName = eventGroupCreateRequestDTO.groupName,
                description = eventGroupCreateRequestDTO.description,
                user = user,
                colorSetId = colorSetId,
                groupCategory = groupCategory
            )
        }
    }
}
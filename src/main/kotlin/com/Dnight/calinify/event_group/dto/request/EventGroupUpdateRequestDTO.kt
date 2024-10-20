package com.dnight.calinify.event_group.dto.request

import jakarta.validation.constraints.Min
import jakarta.validation.constraints.Size

class EventGroupUpdateRequestDTO (
    @field:Min(1)
    val groupId: Long,

    @field:Size(min = 1, max = 20)
    val groupName: String,

    @field:Size(max = 255)
    val description: String? = null,

    @field:Min(1)
    val colorSetId: Int,
)
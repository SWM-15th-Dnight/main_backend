package com.dnight.calinify.ai_process.controller

import com.dnight.calinify.ai_process.dto.request.PlainTextProcessingRequestDTO
import com.dnight.calinify.ai_process.dto.response.ProcessedEventResponseDTO
import com.dnight.calinify.ai_process.service.EventProcessingService
import com.dnight.calinify.config.basicResponse.BasicResponse
import com.dnight.calinify.config.basicResponse.ResponseCode
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/v1/eventProcessing")
class EventProcessingController(
    private val eventProcessingService: EventProcessingService
) {
    @PostMapping("/plainText")
    fun creatPlainTextEvent(@RequestBody plainTextProcessingRequestDTO: PlainTextProcessingRequestDTO,
                            @AuthenticationPrincipal userDetails: UserDetails,
                            ): BasicResponse<ProcessedEventResponseDTO> {
        val userId = userDetails.username.toLong()
        val processedEventResponse = eventProcessingService.createPlainTextEvent(plainTextProcessingRequestDTO, userId)

        return BasicResponse.ok(processedEventResponse, ResponseCode.RequestSuccess)
    }
}
package com.dnight.calinify.ai_process.controller

import com.dnight.calinify.ai_process.dto.request.PlainTextProcessingRequestDTO
import com.dnight.calinify.ai_process.dto.response.ProcessedEventResponseDTO
import com.dnight.calinify.ai_process.service.EventProcessingService
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
    fun creatPlainTextEvent(@RequestBody plainTextProcessingRequestDTO: PlainTextProcessingRequestDTO) : ProcessedEventResponseDTO {

        val processedEventResponse = eventProcessingService.createPlainTextEvent(plainTextProcessingRequestDTO)

        return processedEventResponse
    }
}
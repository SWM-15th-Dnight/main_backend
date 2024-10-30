package com.dnight.calinify.ai_process.controller

import com.dnight.calinify.ai_process.dto.request.ImageProcessingRequestDTO
import com.dnight.calinify.ai_process.dto.request.PlainTextProcessingRequestDTO
import com.dnight.calinify.ai_process.dto.response.ProcessedEventResponseDTO
import com.dnight.calinify.ai_process.service.EventProcessingService
import com.dnight.calinify.config.basicResponse.BasicResponse
import com.dnight.calinify.config.basicResponse.ResponseCode
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.web.bind.annotation.*
import org.springframework.web.multipart.MultipartFile

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

    @PostMapping("/imageProcessing", consumes = ["multipart/form-data"])
    /*
    이미지를 통해 일정 데이터를 만드는 기능
     */
    fun createImageEvent(@RequestParam("file") file: MultipartFile,
                         @RequestParam("promptId") promptId: Int,
                         @RequestParam("inputType") inputType: Int,
                         @AuthenticationPrincipal userDetails: UserDetails,) : BasicResponse<ProcessedEventResponseDTO> {
        val userId = userDetails.username.toLong()
        val imageProcessingRequestDTO = ImageProcessingRequestDTO(promptId, inputType)
        val processedEventResponse = eventProcessingService.createImageEvent(imageProcessingRequestDTO, file, userId)

        return BasicResponse.ok(processedEventResponse, ResponseCode.RequestSuccess)
    }
}
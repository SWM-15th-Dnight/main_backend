package com.dnight.calinify.webclient_module

import com.dnight.calinify.ai_process.dto.to_ai.request.AiImageProcessingRequestDTO
import com.dnight.calinify.ai_process.dto.to_ai.response.AiResponseDTO
import com.dnight.calinify.config.basicResponse.ResponseCode
import com.dnight.calinify.config.exception.ServerSideException
import org.springframework.core.io.ByteArrayResource
import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Component
import org.springframework.util.LinkedMultiValueMap
import org.springframework.web.multipart.MultipartFile
import org.springframework.web.reactive.function.client.WebClient
import org.springframework.web.reactive.function.client.WebClientRequestException
import reactor.core.publisher.Mono
import java.net.ConnectException
import kotlin.reflect.KClass

@Component
class ImageRequest(
    aiRequestBuilder: WebClient.Builder
) {
    private val aiRequestClient = aiRequestBuilder.build()

    fun <T : AiResponseDTO> aiRequest(
        requestDTO: AiImageProcessingRequestDTO,
        imageUUID: String,
        file: MultipartFile,
        responseCls: KClass<T>
    ): ResponseEntity<T>? {

        val body = LinkedMultiValueMap<String, Any>().apply {
            add("promptId", requestDTO.promptId.toString())
            add("imageUUID", imageUUID)
            add("image", object : ByteArrayResource(file.bytes) {
                override fun getFilename() = file.originalFilename
            })
        }
        return aiRequestClient.post()
            .uri("/api/v1/imageProcessing")
            .contentType(MediaType.MULTIPART_FORM_DATA)
            .bodyValue(body)
            .retrieve()
            .toEntity(responseCls.java)
            .onErrorResume { ex ->
                when (ex) {
                    is WebClientRequestException -> {
                        Mono.error(ServerSideException(ResponseCode.AiRequestFail))
                    }

                    is ConnectException -> {
                        Mono.error(ServerSideException(ResponseCode.AiRequestFail))
                    }

                    else -> {
                        Mono.error(RuntimeException("알 수 없는 오류 발생: ${ex.message}"))
                    }
                }
            }
            .block()
    }
}
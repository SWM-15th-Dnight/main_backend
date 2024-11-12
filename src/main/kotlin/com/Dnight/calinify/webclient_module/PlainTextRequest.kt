package com.dnight.calinify.webclient_module

import com.dnight.calinify.ai_process.dto.to_ai.request.AiRequestDTO
import com.dnight.calinify.ai_process.dto.to_ai.response.AiResponseDTO
import com.dnight.calinify.config.basicResponse.ResponseCode
import com.dnight.calinify.config.exception.ServerSideException
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Component
import org.springframework.web.reactive.function.client.WebClient
import org.springframework.web.reactive.function.client.WebClientRequestException
import reactor.core.publisher.Mono
import java.net.ConnectException
import kotlin.reflect.KClass

@Component
class PlainTextRequest(
    aiRequestBuilder: WebClient.Builder
) {
    private val aiRequestClient = aiRequestBuilder.build()

    fun <T : AiResponseDTO> aiRequest(requestDTO : AiRequestDTO, responseCls : KClass<T>) : ResponseEntity<T>? {
        return aiRequestClient.post()
            .uri("/api/v1/plainText")
            .bodyValue(requestDTO)
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
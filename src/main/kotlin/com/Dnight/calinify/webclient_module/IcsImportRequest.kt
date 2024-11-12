package com.dnight.calinify.webclient_module

import com.dnight.calinify.config.basicResponse.ResponseCode
import com.dnight.calinify.config.exception.ServerSideException
import com.dnight.calinify.transport.dto.response.ImportResultResponse
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
class IcsImportRequest(
    transportRequestBuilder: WebClient.Builder,
) {
    private val transportRequestBuilder = transportRequestBuilder.build()

    fun request(
        userId : Long,
        file: MultipartFile,
        responseCls: KClass<ImportResultResponse>
    ): ResponseEntity<ImportResultResponse>? {

        val body = LinkedMultiValueMap<String, Any>().apply {
            add("user_id", userId)
            add("ics_file", object : ByteArrayResource(file.bytes) {
                override fun getFilename() = file.originalFilename
            })
        }
        return transportRequestBuilder.post()
            .uri("/api/v1/import")
            .contentType(MediaType.MULTIPART_FORM_DATA)
            .bodyValue(body)
            .retrieve()
            .toEntity(responseCls.java)
            .onErrorResume { ex ->
                when (ex) {
                    is WebClientRequestException -> {
                        Mono.error(ServerSideException(ResponseCode.TransportRequestFail))
                    }

                    is ConnectException -> {
                        Mono.error(ServerSideException(ResponseCode.TransportRequestFail))
                    }

                    else -> {
                        Mono.error(RuntimeException("알 수 없는 오류 발생: ${ex.message}"))
                    }
                }
            }
            .block()
    }
}
package com.dnight.calinify.webclient_module

import com.dnight.calinify.config.basicResponse.ResponseCode
import com.dnight.calinify.config.exception.ServerSideException
import org.springframework.core.io.ByteArrayResource
import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Component
import org.springframework.web.reactive.function.client.WebClient
import org.springframework.web.reactive.function.client.WebClientRequestException
import reactor.core.publisher.Mono
import java.net.ConnectException
import java.nio.file.Files
import java.nio.file.Paths

@Component
class IcsExportRequest (
    transportRequestBuilder: WebClient.Builder,
    ) {
        private val transportRequestBuilder = transportRequestBuilder.build()

        fun request(
            userId : Long,
            calendarId : Long,
        ): String {
            val resource: ResponseEntity<ByteArrayResource> = transportRequestBuilder.get()
                .uri { builder ->
                    builder
                        .path("/api/v1/export")
                        .queryParam("user_id", userId)
                        .queryParam("calendar_id", calendarId)
                        .build()
                }
                .accept(MediaType.APPLICATION_OCTET_STREAM)
                .retrieve()
                .toEntity(ByteArrayResource::class.java)
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
                .block() ?: throw ServerSideException(ResponseCode.TransportRequestFail)
            // 저장 경로 지정
            val rootPath = Paths.get("").toAbsolutePath().toString()
            val icsRepPath = rootPath + "/src/main/resources/ics_tmp/"

            val outputPath = Paths.get(icsRepPath+resource.body!!.filename)

            // 파일 저장
            Files.write(outputPath, resource.body!!.contentAsByteArray)

            return outputPath.toString() // 저장된 파일 경로 반환
        }
    }
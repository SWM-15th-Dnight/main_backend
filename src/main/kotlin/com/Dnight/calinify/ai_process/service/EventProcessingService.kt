package com.dnight.calinify.ai_process.service

import com.dnight.calinify.ai_process.dto.request.PlainTextProcessingRequestDTO
import com.dnight.calinify.ai_process.dto.response.ProcessedEventResponseDTO
import com.dnight.calinify.ai_process.dto.to_ai.request.AiPlainTextProcessingRequestDTO
import com.dnight.calinify.ai_process.dto.to_ai.request.AiRequestDTO
import com.dnight.calinify.ai_process.dto.to_ai.response.AiPlainTextProcessedResponseDTO
import com.dnight.calinify.ai_process.dto.to_ai.response.AiResponseDTO
import com.dnight.calinify.ai_process.entity.AiProcessingEventEntity
import com.dnight.calinify.ai_process.entity.AiProcessingStatisticsEntity
import com.dnight.calinify.ai_process.repository.AiProcessingEventRepository
import com.dnight.calinify.ai_process.repository.AiProcessingStatisticsRepository
import com.dnight.calinify.config.basicResponse.ResponseCode
import com.dnight.calinify.config.exception.ClientException
import com.dnight.calinify.config.exception.DontRollbackException
import com.dnight.calinify.config.exception.ServerSideException
import com.dnight.calinify.user.repository.UserRepository
import jakarta.transaction.Transactional
import org.springframework.data.repository.findByIdOrNull
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Service
import org.springframework.web.reactive.function.client.WebClient
import org.springframework.web.reactive.function.client.WebClientRequestException
import reactor.core.publisher.Mono
import java.net.ConnectException
import kotlin.reflect.KClass

@Service
class EventProcessingService(
    private val aiProcessingEventRepository: AiProcessingEventRepository,
    private val aiProcessingStatisticsRepository: AiProcessingStatisticsRepository,
    private val userRepository: UserRepository,
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

    @Transactional(dontRollbackOn = [DontRollbackException::class])
    fun createPlainTextEvent(plainTextProcessingRequestDTO: PlainTextProcessingRequestDTO) : ProcessedEventResponseDTO{

        // DTO로부터 ai server에 전달할 내용을 정제
        val aiPlainTextProcessingRequestDTO = AiPlainTextProcessingRequestDTO.from(plainTextProcessingRequestDTO)

        // ai server request
        val aiPlainTextProcessedResponseDTO = aiRequest(aiPlainTextProcessingRequestDTO, AiPlainTextProcessedResponseDTO::class)
            ?: throw ServerSideException(ResponseCode.AiRequestFail)

        // 응답으로부터 body 추출
        val processedResponseBody = aiPlainTextProcessedResponseDTO.body!!

        // TODO User 정보는 더미 데이터, 토큰에서 유저 값 갖고 오기
        val userEntity = userRepository.findByIdOrNull(1) ?: throw ClientException(ResponseCode.UserNotFound)

        // 통계 객체 생성
        val eventProcessedStatisticsEntity = AiProcessingStatisticsEntity.from(userEntity, processedResponseBody, plainTextProcessingRequestDTO)

        // 일정 정보 파악 실패 시 예외 발생
        if (aiPlainTextProcessedResponseDTO.statusCode.isSameCodeAs(HttpStatus.ACCEPTED)) {
            eventProcessedStatisticsEntity.isSuccess = 0
            aiProcessingStatisticsRepository.save(eventProcessedStatisticsEntity)
            throw DontRollbackException(ResponseCode.EventDataNotCaptured)
        }

        // 통계 정보 저장 및 ID 할당
        val eventStatisticsEntity = aiProcessingStatisticsRepository.save(eventProcessedStatisticsEntity)
        val eventProcessingId : Long = eventStatisticsEntity.aiProcessingStatisticsId!!

        // 성공했을 시, processing event 삽입 (실제 유저 event 반영 x)
        val processingEventEntity = AiProcessingEventEntity.from(eventProcessingId, processedResponseBody)
        aiProcessingEventRepository.save(processingEventEntity)

        // 프로세싱 결과에 statistics, event id를 삽입하여 반환
        val processedEventResponseDTO = ProcessedEventResponseDTO.from(
            eventProcessingId, processedResponseBody)

        return processedEventResponseDTO
    }
}
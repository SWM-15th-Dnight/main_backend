package com.dnight.calinify.ai_process.service

import com.dnight.calinify.ai_process.dto.request.ImageProcessingRequestDTO
import com.dnight.calinify.ai_process.dto.request.PlainTextProcessingRequestDTO
import com.dnight.calinify.ai_process.dto.response.ProcessedEventResponseDTO
import com.dnight.calinify.ai_process.dto.to_ai.request.AiImageProcessingRequestDTO
import com.dnight.calinify.ai_process.dto.to_ai.request.AiPlainTextProcessingRequestDTO
import com.dnight.calinify.ai_process.dto.to_ai.response.AiImageProcessedResponseDTO
import com.dnight.calinify.ai_process.dto.to_ai.response.AiPlainTextProcessedResponseDTO
import com.dnight.calinify.ai_process.module.ImageRequest
import com.dnight.calinify.ai_process.module.PlainTextRequest
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
import org.springframework.stereotype.Service
import org.springframework.web.multipart.MultipartFile
import java.util.*

@Service
class EventProcessingService(
    private val aiProcessingEventRepository: AiProcessingEventRepository,
    private val aiProcessingStatisticsRepository: AiProcessingStatisticsRepository,
    private val userRepository: UserRepository,
    private val plainTextRequest: PlainTextRequest,
    private val imageRequest: ImageRequest,
) {
    @Transactional(dontRollbackOn = [DontRollbackException::class])
    fun createPlainTextEvent(plainTextProcessingRequestDTO: PlainTextProcessingRequestDTO,
                             userId : Long) : ProcessedEventResponseDTO{

        // DTO로부터 ai server에 전달할 내용을 정제
        val aiPlainTextProcessingRequestDTO = AiPlainTextProcessingRequestDTO.toDTO(plainTextProcessingRequestDTO)

        // ai server request
        val aiPlainTextProcessedResponseDTO = plainTextRequest.aiRequest(aiPlainTextProcessingRequestDTO, AiPlainTextProcessedResponseDTO::class)
            ?: throw ServerSideException(ResponseCode.AiRequestFail)

        // 응답으로부터 body 추출
        val processedResponseBody = aiPlainTextProcessedResponseDTO.body!!

        // User 조회
        val userEntity = userRepository.findByIdOrNull(userId) ?: throw ClientException(ResponseCode.UserNotFound)

        // 통계 객체 생성
        val eventProcessedStatisticsEntity = AiPlainTextProcessedResponseDTO.toStatisticsEntity(userEntity, processedResponseBody, plainTextProcessingRequestDTO)

        // 일정 정보 파악 실패 시 예외 발생, 실패 경우 저장을 위해 트랜잭션 롤백 X
        if (aiPlainTextProcessedResponseDTO.statusCode.isSameCodeAs(HttpStatus.ACCEPTED)) {
            eventProcessedStatisticsEntity.isSuccess = 0
            aiProcessingStatisticsRepository.save(eventProcessedStatisticsEntity)
            throw DontRollbackException(ResponseCode.EventDataNotCaptured)
        }

        // 통계 정보 저장 및 ID 할당
        val eventStatisticsEntity = aiProcessingStatisticsRepository.save(eventProcessedStatisticsEntity)
        val eventProcessingId : Long = eventStatisticsEntity.aiProcessingStatisticsId!!

        // 성공했을 시, processing event 삽입 (실제 유저 일정이 아닌, 통계용 데이터)
        val processingEventEntity = AiPlainTextProcessedResponseDTO.toEventEntity(eventProcessingId, processedResponseBody)
        aiProcessingEventRepository.save(processingEventEntity)

        // 프로세싱 결과에 event id, statistics를 삽입하여 반환
        val processedEventResponseDTO = ProcessedEventResponseDTO.from(
            eventProcessingId, processedResponseBody)

        return processedEventResponseDTO
    }

    @Transactional(dontRollbackOn = [DontRollbackException::class])
    fun createImageEvent(imageProcessingRequestDTO: ImageProcessingRequestDTO, file: MultipartFile, userId : Long) : ProcessedEventResponseDTO{
        val aiImageProcessingRequestDTO = AiImageProcessingRequestDTO.toDTO(imageProcessingRequestDTO)

        val imageUUID = UUID.randomUUID().toString()

        val imageProcessedResponseDTO = imageRequest.aiRequest(aiImageProcessingRequestDTO, imageUUID, file, AiImageProcessedResponseDTO::class)
            ?: throw ServerSideException(ResponseCode.AiRequestFail)

        // 응답으로부터 body 추출
        val processedResponseBody = imageProcessedResponseDTO.body!!

        // User 조회
        val userEntity = userRepository.findByIdOrNull(userId) ?: throw ClientException(ResponseCode.UserNotFound)

        // 통계 객체 생성
        val eventProcessedStatisticsEntity = AiImageProcessedResponseDTO.toStatisticsEntity(userEntity, processedResponseBody, imageUUID, imageProcessingRequestDTO)

        // 일정 정보 파악 실패 시 예외 발생, 실패 경우 저장을 위해 트랜잭션 롤백 X
        if (imageProcessedResponseDTO.statusCode.isSameCodeAs(HttpStatus.ACCEPTED)) {
            eventProcessedStatisticsEntity.isSuccess = 0
            aiProcessingStatisticsRepository.save(eventProcessedStatisticsEntity)
            throw DontRollbackException(ResponseCode.EventDataNotCaptured)
        }

        // 통계 정보 저장 및 ID 할당
        val eventStatisticsEntity = aiProcessingStatisticsRepository.save(eventProcessedStatisticsEntity)
        val eventProcessingId : Long = eventStatisticsEntity.aiProcessingStatisticsId!!

        // 성공했을 시, processing event 삽입 (실제 유저 일정이 아닌, 통계용 데이터)
        val processingEventEntity = AiImageProcessedResponseDTO.toEventEntity(eventProcessingId, processedResponseBody)
        aiProcessingEventRepository.save(processingEventEntity)

        // 프로세싱 결과에 event id, statistics를 삽입하여 반환
        val processedEventResponseDTO = ProcessedEventResponseDTO.from(
            eventProcessingId, processedResponseBody)

        return processedEventResponseDTO

    }
}
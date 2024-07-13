package com.dnight.calinify.config.basicResponse

import org.springframework.http.HttpStatus
import org.springframework.http.HttpStatusCode
import org.springframework.http.ResponseEntity

data class BasicResponse<ResponseDTO> (
    val data: ResponseDTO?,
    val statusCode: ResponseCode,
    ) : ResponseEntity<ResponseDTO>(data, HttpStatusCode.valueOf(statusCode.statusCode)) {
        companion object {
            fun ok(data: ResponseDTO, statusCode: ResponseCode): BasicResponse<ResponseDTO> {
                return BasicResponse(data, statusCode)
            }
            fun fail(statusCode: ResponseCode): BasicResponse<ResponseDTO> {
                return BasicResponse(ErrorResponseDTO(statusCode.message), statusCode)
            }
        }
    }
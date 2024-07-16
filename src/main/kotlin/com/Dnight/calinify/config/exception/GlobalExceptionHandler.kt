package com.dnight.calinify.config.exception

import com.dnight.calinify.config.basicResponse.BasicResponse
import com.dnight.calinify.config.basicResponse.ExceptionResponse
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler

@ControllerAdvice
class GlobalExceptionHandler {

    @ExceptionHandler(ClientException::class)
    fun handleClientException(e: ClientException) : BasicResponse<ExceptionResponse> {
        return BasicResponse.fail(e.responseCode)
    }

    @ExceptionHandler(ServerSideException::class)
    fun handleServerSideException(e: ServerSideException) : BasicResponse<ExceptionResponse> {
        return BasicResponse.fail(e.responseCode)
    }
}
package com.dnight.calinify.config.exception

import com.dnight.calinify.config.basicResponse.BasicResponse
import com.dnight.calinify.config.basicResponse.ExceptionResponse
import com.dnight.calinify.config.basicResponse.ResponseCode
import org.springframework.http.converter.HttpMessageNotReadableException
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler

@ControllerAdvice
class GlobalExceptionHandler {

    @ExceptionHandler(HttpMessageNotReadableException::class)
    fun handleHttpMessageNotReadableException(ex: HttpMessageNotReadableException): BasicResponse<ExceptionResponse> {
        return BasicResponse.fail(ResponseCode.JsonKeyNotValid)
    }

    @ExceptionHandler(ClientException::class)
    fun handleClientException(ex: ClientException): BasicResponse<ExceptionResponse> {
        return BasicResponse.fail(ex.responseCode)
    }

    @ExceptionHandler(ServerSideException::class)
    fun handleServerSideException(ex: ServerSideException): BasicResponse<ExceptionResponse> {
        return BasicResponse.fail(ex.responseCode)
    }
}
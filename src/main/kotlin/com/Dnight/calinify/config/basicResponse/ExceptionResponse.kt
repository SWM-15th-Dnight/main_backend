package com.dnight.calinify.config.basicResponse

class ExceptionResponse(
    val message: String
)

class FailedValidationResponse<T>(
    val message: String,
    val detail: T,
)
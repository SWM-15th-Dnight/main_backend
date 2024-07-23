package com.dnight.calinify.config.basicResponse

/**
 * 비즈니스 로직에서 캐치 가능한 예외에 대해, `BasicResponse<ExceptionResponse>`와 같은 형식으로 반환하기 위한 wrapper 클래스
 *
 *
 *
 * message는 ResponseCode마다 함께 선언된 예외 메시지가 자동으로 삽입된다.
 */
class ExceptionResponse(
    val message: String
)

class FailedValidationResponse<T>(
    val message: String,
    val detail: T,
)
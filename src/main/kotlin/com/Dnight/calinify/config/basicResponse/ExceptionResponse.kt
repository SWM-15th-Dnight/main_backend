package com.dnight.calinify.config.basicResponse

/**
 * 비즈니스 로직에서 캐치 가능한 예외에 대해, `BasicResponse<ExceptionResponse>`와 같은 형식으로 반환하기 위한 wrapper 클래스
 *
 * message는 ResponseCode마다 함께 선언된 예외 메시지가 자동으로 삽입된다.
 */
class ExceptionResponse(
    val message: String,
)

/**
 * ResponseCode의 메세지에 추가적인 detail message가 필요할 때 사용
 */
class DetailExceptionResponse(
    val detailMessage : String
)

/**
 * http reqeust에서 DTO validation error 발생 시 활용
 */

class FailedValidationResponse<T>(
    val message: String,
    val detail: T,
)
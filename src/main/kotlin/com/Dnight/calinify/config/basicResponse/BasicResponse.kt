package com.dnight.calinify.config.basicResponse

import org.springframework.http.HttpStatusCode
import org.springframework.http.ResponseEntity

/**
 *  커스텀 Response 객체의 최상위 클래스
 *
 *  Spring의 기본 Repsonse 객체인 [ResponseEntity]를 상속받아 사용하여, `ResponseEntity`를 대체한다.
 *
 *  `BasicResponse(TestDTO, 201)`처럼 쓰지 않으며,
 *
 *  ```
 *  BasicResponse.ok(TestDTO, ResponseCode.ResponseSuccess)
 *  BasicResponse.fail(ResponseCode.ResourceNotFound)
 *  ```
 *
 *  등의 컴패니언 오브젝트 함수 (java의 static 메서드)를 활용하여 controller에서 객체 생성 및 반환한다.
 *
 *
 * @property data Response의 body - 응답 내용 및 에러 메시지를 반환한다.
 * @property statusCode Repsonse의 http 응답 코드,
 * Enum으로 정의된 커스텀 응답 코드 객체가 담기며,
 * 객체 내부의 정수형 statusCode가 http 표준 응답 코드로 전송된다.
 * @return ResponseEntity<ResponseDTO>
 *
 * @author 정인모
 */
data class BasicResponse<T> (
    val data: T,
    val responseCode: ResponseCode,
    ) : ResponseEntity<T>(data, HttpStatusCode.valueOf(responseCode.statusCode)) {
        companion object {
            /**
             * 정상적인 응답일 경우 반환한다.
             *
             * @param data Response의 Body, 내용이 담긴다.
             * @param responseCode Enum 클래스인 [ResponseCode] 객체의 상황에 맞는 문자 코드를 기입하면 자동으로 정수형의 http 코드가 들어간다.
             */
            fun <T> ok(data: T, responseCode: ResponseCode): BasicResponse<T> {
                return BasicResponse(data, responseCode)
            }

            /**
             * Try - Catch에서 exception이 발생한 경우, 지정된 에러 메시지와 코드를 반환한다.
             *
             * @param responseCode Enum 클래스 [ResponseCode] 객체에 기입된 메시지와 코드가 자동으로 매핑되어 에러 메시지로 전송된다.
             */
            fun fail(responseCode: ResponseCode): BasicResponse<ExceptionResponse> {
                return BasicResponse(ExceptionResponse(responseCode.message), responseCode)
            }

            fun fail(responseCode: ResponseCode, detailMessage : DetailExceptionResponse): BasicResponse<DetailExceptionResponse> {
                return BasicResponse(detailMessage, responseCode)
            }

            /**
             * request의 값 검증이 실패한 경우, 지정된 에러 메시지와 코드 및 필드 내역을 반환한다.
             *
             * 또는 추가적인 지침이 필요한 에러에 대해 디테일 메시지를 더해준다.
             */
            fun <T>fail(responseCode: ResponseCode, failedValidationData: T): BasicResponse<FailedValidationResponse<T>> {
                return BasicResponse(FailedValidationResponse(responseCode.message, failedValidationData), responseCode)
            }
        }
    }
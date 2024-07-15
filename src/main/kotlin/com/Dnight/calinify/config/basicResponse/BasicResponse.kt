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
data class BasicResponse<ResponseDTO> (
    val data: ResponseDTO,
    val statusCode: ResponseCode,
    ) : ResponseEntity<ResponseDTO>(data, HttpStatusCode.valueOf(statusCode.statusCode)) {
        companion object {
            /**
             * 정상적인 응답일 경우 반환한다.
             *
             * @param data Response의 Body, 내용이 담긴다.
             * @param statusCode Enum 클래스인 [ResponseCode] 객체의 상황에 맞는 코드를 기입하면 자동으로 정수형의 http 코드가 들어간다.
             */
            fun ok(data: ResponseDTO, statusCode: ResponseCode): BasicResponse<ResponseDTO> {
                return BasicResponse(data, statusCode)
            }

            /**
             * Try - Catch에서 exception이 발생한 경우, 지정된 에러 메시지와 코드를 반환한다.
             *
             * @param statusCode Enum 클래스 [ResponseCode] 객체에 기입된 메시지와 코드가 자동으로 매핑되어 에러 메시지로 전송된다.
             */
            fun fail(statusCode: ResponseCode): BasicResponse<ResponseDTO> {
                return BasicResponse(ErrorResponseDTO(statusCode.message), statusCode)
            }

        }
    }
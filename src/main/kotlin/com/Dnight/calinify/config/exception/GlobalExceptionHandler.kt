package com.dnight.calinify.config.exception

import com.dnight.calinify.config.basicResponse.*
import org.springframework.dao.DataIntegrityViolationException
import org.springframework.http.converter.HttpMessageNotReadableException
import org.springframework.validation.FieldError
import org.springframework.web.bind.MethodArgumentNotValidException
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler

@ControllerAdvice
class GlobalExceptionHandler {

    /**
     * `@field:max` 등 DTO의 검증 단계에서 규격이 맞지 않는 데이터가 들어올 경우 예외를 발생시킨다.
     *
     * Int로 선언된 DTO 필드는 값이 들어올 때, Non-nullable로 선언된 경우, 자바 변환 과정에서 원시 타입으로 바뀌어, 기본값이 0이 되는 이슈가 있음.
     *
     * 따라서 정수형 필드는 반드시 `@field:Min(1)` 등을 달아주며, 이외에도 검증이 필요한 값들은 가능하다면 세세하게 달아준다.
     *
     * 에러가 발생할 경우, 상세 메시지로 필드와 검증 방식이 반환된다.
     *
     * @author 정인모
     */
    @ExceptionHandler(MethodArgumentNotValidException::class)
    fun handleValidationExceptions(ex: MethodArgumentNotValidException): BasicResponse<FailedValidationResponse<Map<String, String?>>> {
        val errors = mutableMapOf<String, String?>()
        ex.bindingResult.allErrors.forEach { error ->
            val fieldName = (error as FieldError).field
            val errorMessage = error.getDefaultMessage()
            errors[fieldName] = errorMessage
        }
        return BasicResponse.fail(ResponseCode.InputDataNotValid, errors)
    }

    /**
     * jakarta Validation으로 걸러지지 않는 미상의 Input Data 에러
     *
     * @author 정인모
     */
    @ExceptionHandler(HttpMessageNotReadableException::class)
    fun handleHttpMessageNotReadableException(ex: HttpMessageNotReadableException): BasicResponse<FailedValidationResponse<String>> {
        val error = ex.localizedMessage
        return BasicResponse.fail(ResponseCode.InputDataNotValid, error)
    }

    /**
     * Unique 제약으로 인해, 데이터를 입력할 수 없는 경우 발생하는 에러
     *
     * DB 쿼리 단에서 발생하는 예외이므로, 해당 메시지가 발생했다는 것은 `서비스 단에서 exception처리가 제대로 이루어지지 않았음`을 의미함.
     *
     * 당연히 DB의 값과 비교해야 하므로, db에 query가 날아가고, 반환된 query가 에러메시지에 담기므로 exception의 메시지를 반환시킬 수 없다.
     *
     * 코드의 기본 메시지 반환한다.
     *
     * 다만, unique 제약이 걸린 곳은 한정적이므로, 잘 찾아가면 된다.
     *
     * 가능하면 Client exception에서 `ResponseCode.DuplicatedInputData`로 반환될 수 있도록 만들자.
     *
     * @author 정인모
     */
    @ExceptionHandler(DataIntegrityViolationException::class)
    fun handleDataIntegrityViolationException(ex: DataIntegrityViolationException): BasicResponse<ExceptionResponse> {

        return BasicResponse.fail(ResponseCode.DuplicatedInputData)
    }

    /**
     * client(사용자 및 프론트엔드 단)측의 잘못으로 처리된 예외
     *
     * 사용 예시:
     *
     * 추가 요망
     *
     * @author 정인모
     */
    @ExceptionHandler(ClientException::class)
    fun handleClientException(ex: ClientException): BasicResponse<ExceptionResponse> {
        return BasicResponse.fail(ex.responseCode)
    }

    /**
     * server(스프링 서버) 측의 잘못으로 처리된 예외
     *
     * 사용 예시 :
     *
     * 추가 요망
     *
     * @author 정인모
     */

    @ExceptionHandler(ServerSideException::class)
    fun handleServerSideException(ex: ServerSideException): BasicResponse<ExceptionResponse> {
        return BasicResponse.fail(ex.responseCode)
    }
}
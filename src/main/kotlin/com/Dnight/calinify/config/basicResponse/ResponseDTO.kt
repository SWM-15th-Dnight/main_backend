package com.dnight.calinify.config.basicResponse

/**
 * ResponseDTO 객체의 최상위 클래스
 *
 * 사용 이유 :
 *
 * 1. Controller에서 반환하는 모든 Response는 표준 응답 객체인 ResponseEntity&lt;T&gt;를 반환하나, 제너릭 클래스를 함수마다 따로 설정해줘야 한다.
 *
 * 2. 또한 에러 코드와 메시지만을 반환하기 힘들다.
 *
 * 따라서 모든 Response data transfer object는 해당 ResponseDTO를 상속받아 사용하며,
 *
 * ReseponsDTO는 커스텀 ResponseEntity 클래스인, [BasicResponse]로 래핑한다.
 *
 * ```
 * fun TestController(param : Int) : BasicResponse<ResponseDTO> {...}
 * ```
 *
 * 자세한 사항은 아래 참조
 * @author 정인모
 * @see BasicResponse
 */
open class ResponseDTO() {}

/**
 * 커스텀 ErrorResponse 객체의 최상위 클래스
 *
 * 커스텀 에러 코드 및 메시지를 반환하기 위해 ResponseDTO를 상속받는다.
 *
 * 따라서 하나의 컨트롤러 내에서 에러 유형에 따라 다른 값을 반환하기 위해
 *
 * 1. DTO 객체마다 에러 메시지 필드를 생성할 필요없이
 *
 * 2. null이 사용되는 것을 최소화한다.
 *
 * Controller에서는 해당 클래스를 직접 선언하는 일 없이, [BasicResponse]를 빌드하는 과정에서,
 *
 * [BasicResponse.ok], [BasicResponse.fail] 메서드 사용 시 자동으로 메시지와 에러코드를 래핑하는 래퍼 클래스로써 사용된다.
 *
 * @author 정인모
 */
class ErrorResponseDTO(val message: String) : ResponseDTO()
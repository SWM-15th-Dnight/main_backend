package com.dnight.calinify.config.basicResponse

/**
 * 커스텀 에러 코드 클래스
 *
 * 기본적으로 표준 HTTP 응답 코드를 따라간다.
 *
 * 다만 추상적이고 포괄적인 응답 코드에서 세부적인 커스텀 코드를 정의해
 *
 * 개발, 운영 시의 편의성과 확장성을 높인다.
 *
 * BasicResponse 객체를 생성할 때 사용하며, 용법은 다음과 같다.
 *
 * ```kotlin
 * return BasicResponse.ok(data, ResponseCode.ResponseSuccess)
 * ```
 *
 * @author 정인모
 */
enum class ResponseCode(val statusCode: Int, var message: String) {

    // 200 - 요청 처리 성공

    ResponseSuccess(200, "응답 성공"),
    UpdateSuccess(200, "업데이트 성공"),
    RequestSuccess(200, "요청 - 응답 성공"),

    CreateSuccess(201, "리소스 생성 성공"),

    DeleteSuccess(200, "리소스 삭제 성공"),

    // 400 - 클라이언트 에러

    ImpassableDate(400, "불가능한 날짜에 입력 / 조회"),

    Unauthorized(401, "인증이 없음"),
    DisallowedToken(401, "토큰이 만료됨"),
    FailedLogin(401, "로그인 Email, 비밀번호가 맞지 않음"),

    NotYourResource(403, "해당 유저의 데이터가 아님"),
    Forbidden(403, "접근 권한 없음"),

    NotFound(404, "데이터를 찾을 수 없음"),
    NotFoundOrNotMatchUser(404, "데이터가 없거나, 해당 유저의 것이 아님"),
    UserNotFound(404, "유저를 찾을 수 없음"),

    AlreadyExists(409, "이미 존재하는 데이터"),
    AlreadyExistsUser(409, "이미 존재하는 유저 데이터"),

    DeletedResource(410, "삭제된 데이터"),

    LargeText(413, "입력한 데이터의 길이가 너무 긺"),

    EventDataNotCaptured(422, "이벤트 데이터 포착 실패"),

    InputDataNotValid(422, "입력한 데이터의 포맷이 맞지 않음"),

    DuplicatedInputData(422, "중복된 데이터"),

    // 500 - 서버 에러
    DataSaveFailed(500, "데이터 저장 실패"),
    DeleteFailed(500, "리소스 삭제 실패"),

    AiRequestFail(500, "Ai 서버와 통신 실패"),
    TransportRequestFail(500, "Transport 서버와 통신 실패")
    ;
}
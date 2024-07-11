package com.dnight.calinify.config.basicResponse

enum class ResponseCode(val statusCode: Int, val message: String) {

    ResponseSuccess(200, "응답 성공"),
    CreateSuccess(201, "생성 성공"),
    Unauthorized(401, "로그인 필요"),
    NotFound(404, "리소스를 찾을 수 없음");
}
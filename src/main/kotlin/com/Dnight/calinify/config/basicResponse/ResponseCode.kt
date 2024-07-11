package com.dnight.calinify.config.basicResponse

enum class ResponseCode(val statusCode: Int, val message: String) {

    ResponseSuccess(200, "응답 성공"),
    CreateSuccess(201, "생성 성공"),
    Unauthorized(401, "로그인 필요");
}
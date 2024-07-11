package com.dnight.calinify.config.basicResponse

class BasicResponse private constructor(header: BasicHeader, data: Any) {

    companion object {
        fun body(code: ResponseCode, content: Any): BasicResponse {
            return BasicResponse(BasicHeader.createHeader(code.statusCode, code.message), content)
        }
    }
}
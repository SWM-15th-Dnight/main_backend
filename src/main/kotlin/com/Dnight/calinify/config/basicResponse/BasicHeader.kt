package com.dnight.calinify.config.basicResponse

class BasicHeader private constructor(code : Int, message : String) {

    companion object {
        fun createHeader(statusCode : Int, message: String) : BasicHeader{
            return BasicHeader(statusCode, message)
        }
    }
}
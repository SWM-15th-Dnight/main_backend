package com.dnight.calinify.config.exception

import com.dnight.calinify.config.basicResponse.ResponseCode

class ClientException(
    val responseCode: ResponseCode,
    override val message: String = responseCode.message,
) : RuntimeException()
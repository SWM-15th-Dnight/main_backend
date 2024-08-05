package com.dnight.calinify.config.exception

import com.dnight.calinify.config.basicResponse.ResponseCode

class ClientException(
    val responseCode: ResponseCode,
    val detail : String? = null,
) : RuntimeException()
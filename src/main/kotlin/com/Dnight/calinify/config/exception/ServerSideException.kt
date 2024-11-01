package com.dnight.calinify.config.exception;

import com.dnight.calinify.config.basicResponse.ResponseCode

class ServerSideException(
    val responseCode: ResponseCode,
) : RuntimeException()

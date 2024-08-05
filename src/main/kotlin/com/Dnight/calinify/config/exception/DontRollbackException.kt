package com.dnight.calinify.config.exception

import com.dnight.calinify.config.basicResponse.ResponseCode

class DontRollbackException (
    val responseCode: ResponseCode,
) : RuntimeException()
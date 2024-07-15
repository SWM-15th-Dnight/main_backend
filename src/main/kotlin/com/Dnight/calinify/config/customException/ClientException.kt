package com.dnight.calinify.config.customException

class ResourceNotFound(message: String = "리소스를 찾지 못함") : RuntimeException(message)
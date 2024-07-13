package com.dnight.calinify.config.basicResponse

open class ResponseDTO() {}

class ErrorResponseDTO(val message: String) : ResponseDTO()
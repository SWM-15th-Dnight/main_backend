package com.dnight.calinify.transport.service

import com.dnight.calinify.config.basicResponse.ResponseCode
import com.dnight.calinify.config.exception.ServerSideException
import com.dnight.calinify.transport.dto.response.ImportResultResponse
import com.dnight.calinify.webclient_module.IcsExportRequest
import com.dnight.calinify.webclient_module.IcsImportRequest
import jakarta.transaction.Transactional
import org.springframework.stereotype.Service
import org.springframework.web.multipart.MultipartFile

@Service
class TransportService(
    val icsImportRequest: IcsImportRequest,
    val icsExportRequest: IcsExportRequest,
) {
    @Transactional
    fun icsImport(userId: Long, icsFile : MultipartFile) : ImportResultResponse {

        val importResultResponse = icsImportRequest.request(userId, icsFile, ImportResultResponse::class)
            ?: throw ServerSideException(ResponseCode.TransportRequestFail)

        val importResultResponseBody = importResultResponse.body!!

        return importResultResponseBody
    }

    @Transactional
    fun icsExport(userId: Long, calendarId : Long) : String {

        val exportResultResponse = icsExportRequest.request(userId = userId, calendarId = calendarId)

        return exportResultResponse
    }
}
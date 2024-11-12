package com.dnight.calinify.transport.controller

import com.dnight.calinify.config.basicResponse.BasicResponse
import com.dnight.calinify.config.basicResponse.ResponseCode
import com.dnight.calinify.transport.dto.response.ImportResultResponse
import com.dnight.calinify.transport.service.TransportService
import org.springframework.core.io.Resource
import org.springframework.core.io.UrlResource
import org.springframework.http.HttpHeaders
import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.*
import org.springframework.web.multipart.MultipartFile
import java.nio.file.Path
import java.nio.file.Paths


@Validated
@RestController
@RequestMapping("/api/v1/transport")
class TransportController(
    private val transportService: TransportService
) {
    @PostMapping("/import", consumes = ["multipart/form-data"])
    fun icsImport(@RequestParam("file") file: MultipartFile,
                  @AuthenticationPrincipal userDetails: UserDetails,) : BasicResponse<ImportResultResponse>{
        val userId = userDetails.username.toLong()
        val importResponse = transportService.icsImport(userId, file)

        return BasicResponse.ok(importResponse, ResponseCode.ResponseSuccess)
    }

    @GetMapping("/export", produces = ["multipart/form-data"])
    fun icsExport(@RequestParam calendarId : Long,
                  @AuthenticationPrincipal userDetails: UserDetails,) : ResponseEntity<Resource>{
        val userId = userDetails.username.toLong()
        val filePath: String = transportService.icsExport(calendarId, userId)
        val path: Path = Paths.get(filePath)
        val resource: Resource = UrlResource(path.toUri())

        return ResponseEntity.ok()
            .contentType(MediaType.parseMediaType("text/calendar")) // .ics 파일의 MIME 타입
            .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename="+path.fileName)
            .body(resource)
    }
}
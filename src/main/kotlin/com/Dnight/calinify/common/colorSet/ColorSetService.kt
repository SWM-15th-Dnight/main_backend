package com.dnight.calinify.common.colorSet

import com.dnight.calinify.config.basicResponse.ResponseCode
import com.dnight.calinify.config.exception.ClientException
import com.dnight.calinify.config.exception.ServerSideException
import jakarta.transaction.Transactional
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service


@Service
class ColorSetService(
    private val colorSetRepository: ColorSetRepository
) {
    fun getColorSet(colorSetId : Int) : ColorSetResponseDTO {

        val colorSet = colorSetRepository.findByIdOrNull(colorSetId) ?: throw ClientException(ResponseCode.NotFound)

        return ColorSetResponseDTO.from(colorSet)
    }

    fun getAllColorSets(): List<ColorSetResponseDTO> {

        val colorSets = colorSetRepository.findAll()

        return colorSets.map { ColorSetResponseDTO.from(it) }
    }

    @Transactional
    fun createColorSet(colorSetRequestDTO: ColorSetRequestDTO) : Boolean {

        val colorSetEntity = ColorSetRequestDTO.toEntity(colorSetRequestDTO)

        try {
            colorSetRepository.save(colorSetEntity)
        } catch (ex : Exception) {
            throw ServerSideException(ResponseCode.DataSaveFailed)
        }

        return true
    }

    @Transactional
    fun updateColorSet(colorSetRequestDTO: ColorSetRequestDTO) : Boolean {

        val colorSet = colorSetRepository.findByIdOrNull(colorSetRequestDTO.colorSetId)
            ?: throw ClientException(ResponseCode.NotFound)

        colorSet.colorName = colorSetRequestDTO.colorSetName
        colorSet.hexCode = colorSetRequestDTO.hexCode

        return true
    }

    @Transactional
    fun deleteColorSet(colorSetId: Int): Boolean {

        try {
            colorSetRepository.deleteById(colorSetId)
        } catch (ex : Exception) {
            throw ServerSideException(ResponseCode.DeleteFailed)
        }

        return true
    }
}
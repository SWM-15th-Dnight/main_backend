package com.dnight.calinify.common.inputType

import com.dnight.calinify.config.basicResponse.ResponseCode
import com.dnight.calinify.config.exception.ClientException
import com.dnight.calinify.config.exception.ServerSideException
import jakarta.transaction.Transactional
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service

@Service
class InputTypeService(
    private val inputTypeRepository: InputTypeRepository
) {
    fun getInputTypeById(inputTypeId : Int) : InputTypeResponseDTO {
        val inputType = inputTypeRepository.findByIdOrNull(inputTypeId)
            ?: throw ClientException(ResponseCode.NotFound, "input type id")

        return InputTypeResponseDTO(inputType.inputTypeId!!, inputType.inputType!!)
    }

    fun getAllInputTypes() : List<InputTypeResponseDTO> {

        val inputTypes = inputTypeRepository.findAll()

        return inputTypes.map { InputTypeResponseDTO.from(it) }
    }

    @Transactional
    fun createInputType(inputTypeRequestDTO: InputTypeRequestDTO) : Boolean {

        val inputType = InputTypeEntity(null, inputTypeRequestDTO.inputType)

        inputTypeRepository.save(inputType)

        return true
    }

    @Transactional
    fun updateInputType(inputTypeId: Int, inputTypeRequestDTO: InputTypeRequestDTO) : Boolean {

        val inputType = inputTypeRepository.findByIdOrNull(inputTypeId)
            ?: throw ClientException(ResponseCode.NotFound, "input type id")

        inputType.inputType = inputTypeRequestDTO.inputType

        return true
    }

    @Transactional
    fun deleteInputType(inputTypeId: Int) : Boolean {
        try {
            inputTypeRepository.deleteById(inputTypeId)
        } catch (e: Exception) {
            throw ServerSideException(ResponseCode.DeleteFailed)
        }

        return true
    }
}
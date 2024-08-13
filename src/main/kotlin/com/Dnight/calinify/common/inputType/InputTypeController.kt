package com.dnight.calinify.common.inputType

import com.dnight.calinify.config.basicResponse.BasicResponse
import com.dnight.calinify.config.basicResponse.ResponseCode
import com.dnight.calinify.config.basicResponse.ResponseOk
import org.springframework.web.bind.annotation.*


@RestController
@RequestMapping("/inputType")
class InputTypeController(
    private val inputTypeService: InputTypeService
) {
    @GetMapping("/{inputTypeId}")
    fun getInputTypeById(@PathVariable inputTypeId : Int) : BasicResponse<InputTypeResponseDTO> {

        val inputType = inputTypeService.getInputTypeById(inputTypeId)

        return BasicResponse.ok(inputType, ResponseCode.ResponseSuccess)
    }

    @GetMapping("/")
    fun getAllInputType() : BasicResponse<List<InputTypeResponseDTO>> {

        val inputTypes = inputTypeService.getAllInputTypes()

        return BasicResponse.ok(inputTypes, ResponseCode.ResponseSuccess)
    }

    @PostMapping("/")
    fun createInputType(@RequestBody inputTypeRequestDTO: InputTypeRequestDTO) : BasicResponse<ResponseOk> {

        inputTypeService.createInputType(inputTypeRequestDTO)

        return BasicResponse.ok(ResponseOk(), ResponseCode.CreateSuccess)
    }

    @PutMapping("/{inputTypeId}")
    fun updateInputType(@PathVariable inputTypeId: Int,
                        @RequestBody inputTypeRequestDTO: InputTypeRequestDTO): BasicResponse<ResponseOk> {

        inputTypeService.updateInputType(inputTypeId, inputTypeRequestDTO)

        return BasicResponse.ok(ResponseOk(), ResponseCode.UpdateSuccess)
    }

    @DeleteMapping("/{inputTypeId}")
    fun deleteInputType(@PathVariable inputTypeId : Int) : BasicResponse<ResponseOk> {

        inputTypeService.deleteInputType(inputTypeId)

        return BasicResponse.ok(ResponseOk(), ResponseCode.DeleteSuccess)
    }
}
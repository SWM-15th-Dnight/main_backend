package com.dnight.calinify.common.colorSet

import com.dnight.calinify.config.basicResponse.BasicResponse
import com.dnight.calinify.config.basicResponse.ResponseCode
import com.dnight.calinify.config.basicResponse.ResponseOk
import org.springframework.web.bind.annotation.*


@RestController
@RequestMapping("/colorSet")
class ColorSetController(
    private val colorSetService: ColorSetService,
) {
    @GetMapping("/{colorSetId}")
    fun getColorSet(@PathVariable colorSetId : Int) : BasicResponse<ColorSetResponseDTO> {

        val colorSet = colorSetService.getColorSet(colorSetId)

        return BasicResponse.ok(colorSet, ResponseCode.ResponseSuccess)
    }

    @GetMapping("/")
    fun getAllColorSet() : BasicResponse<List<ColorSetResponseDTO>> {

        val colorSets : List<ColorSetResponseDTO> = colorSetService.getAllColorSets()

        return BasicResponse.ok(colorSets, ResponseCode.ResponseSuccess)
    }

    @PostMapping("/")
    fun createColorSet(@RequestBody colorSetRequestDTO: ColorSetRequestDTO) : BasicResponse<ResponseOk> {

        colorSetService.createColorSet(colorSetRequestDTO)

        return BasicResponse.ok(ResponseOk(), ResponseCode.CreateSuccess)
    }

    @PutMapping("/")
    fun updateColorSet(@RequestBody colorSetRequestDTO: ColorSetRequestDTO) : BasicResponse<ResponseOk> {

        colorSetService.updateColorSet(colorSetRequestDTO)

        return BasicResponse.ok(ResponseOk(), ResponseCode.UpdateSuccess)
    }

    @DeleteMapping("/{colorSetId}")
    fun deleteColorSet(@PathVariable colorSetId : Int) : BasicResponse<ResponseOk> {

        colorSetService.deleteColorSet(colorSetId)

        return BasicResponse.ok(ResponseOk(), ResponseCode.DeleteSuccess)
    }
}
package com.efishery.fetchapp.adapter.output.webclient.converter

import com.efishery.fetchapp.adapter.output.webclient.dto.FreeCurrencyAPIDTO
import com.efishery.fetchapp.adapter.output.webclient.dto.StoragesDto
import com.efishery.fetchapp.domain.entity.Dollar
import com.efishery.fetchapp.domain.entity.Storages
import org.springframework.stereotype.Component

@Component
class StoragesConverter {
    fun convertDTOtoEntityStoragesWeb(dto: StoragesDto): Storages? {
        val entity = Storages()
        entity.uuid = dto.uuid
        entity.komoditas = dto.komoditas
        entity.areaProvinsi = dto.areaProvinsi
        entity.areaKota = dto.areaKota
        entity.size = dto.size?.toInt()
        entity.price = dto.price?.toInt()
        entity.tglParsed = dto.tglParsed
        entity.timestamp = dto.timestamp

        return entity
    }

    fun convertFreeCurrencyDTOtoDollarEntity(dto: FreeCurrencyAPIDTO): Dollar? {
        val entity = Dollar()
        entity.value = dto.data?.USD
        return entity
    }
}
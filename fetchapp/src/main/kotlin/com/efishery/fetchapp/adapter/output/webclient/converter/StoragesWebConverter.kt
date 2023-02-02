package com.efishery.fetchapp.adapter.output.webclient.converter

import com.efishery.fetchapp.adapter.output.webclient.dto.StoragesWebDto
import com.efishery.fetchapp.domain.entity.StoragesWeb
import org.springframework.stereotype.Component

@Component
class StoragesWebConverter {
    fun convertDTOtoEntityStoragesWeb(dto: StoragesWebDto): StoragesWeb? {
        val entity : StoragesWeb = StoragesWeb()
        entity.uuid = dto.uuid
        entity.komoditas = dto.komoditas
        entity.areaProvinsi = dto.areaProvisi
        entity.areaKota = dto.areaKota
        entity.size = dto.size.toInt()
        entity.price = dto.price.toInt()
        entity.tglParsed = dto.tglParsed
        entity.timestamp = dto.timestamp

        return entity
    }
}
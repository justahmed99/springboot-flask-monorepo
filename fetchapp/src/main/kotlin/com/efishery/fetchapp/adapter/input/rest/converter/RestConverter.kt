package com.efishery.fetchapp.adapter.input.rest.converter

import com.efishery.fetchapp.adapter.input.rest.dto.StoragesResponse
import com.efishery.fetchapp.domain.entity.Storages
import org.springframework.stereotype.Component

@Component
class RestConverter {
    fun convertStoragesEntityToStoragesResponse(entity: Storages): StoragesResponse {
        val response = StoragesResponse()
        response.uuid = entity.uuid
        response.komoditas = entity.komoditas
        response.areaProvinsi = entity.areaProvinsi
        response.areaKota = entity.areaKota
        response.size = entity.size
        response.price = entity.price
        response.tglParsed = entity.tglParsed
        response.timestamp = entity.timestamp
        return response
    }

    fun addUSDValue(storagesResponseList: MutableList<StoragesResponse?>, dollar: Double): MutableList<StoragesResponse?> {
        storagesResponseList.forEach { it?.priceUSD = dollar * it?.price!! }
        return storagesResponseList
    }
}
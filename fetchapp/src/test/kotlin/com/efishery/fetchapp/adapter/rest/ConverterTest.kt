package com.efishery.fetchapp.adapter.rest

import com.efishery.fetchapp.adapter.input.rest.converter.RestConverter
import com.efishery.fetchapp.adapter.input.rest.dto.StoragesResponse
import com.efishery.fetchapp.domain.entity.Storages
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest

@SpringBootTest
class ConverterTest {

    @Autowired
    private val restConverter: RestConverter? = null

    private var storages = Storages()
    private var storagesResponse = StoragesResponse()

    @BeforeEach
    fun setup() {
        storages.uuid = "a4ba246f-a1fd-4797-ab67-9a64eb3f3161"
        storages.komoditas = "GURAME"
        storages.size = 180
        storages.price = 91000
        storages.timestamp = "1641016968903"
        storages.areaProvinsi = "JAWA BARAT"
        storages.areaKota = "CILILIN"
        storages.tglParsed = "2022-01-01T06:02:48"
    }

    @Test
    fun convertStoragesEntityToStoragesResponseTest() {
        storagesResponse = restConverter!!.convertStoragesEntityToStoragesResponse(storages)

        Assertions.assertEquals(storages.uuid, storagesResponse.uuid)
        Assertions.assertEquals(storages.komoditas, storagesResponse.komoditas)
        Assertions.assertEquals(storages.size, storagesResponse.size)
        Assertions.assertEquals(storages.timestamp, storagesResponse.timestamp)
        Assertions.assertEquals(storages.price, storagesResponse.price)
        Assertions.assertEquals(storages.areaProvinsi, storagesResponse.areaProvinsi)
        Assertions.assertEquals(storages.areaKota, storagesResponse.areaKota)
        Assertions.assertEquals(storages.tglParsed, storagesResponse.tglParsed)
    }
}
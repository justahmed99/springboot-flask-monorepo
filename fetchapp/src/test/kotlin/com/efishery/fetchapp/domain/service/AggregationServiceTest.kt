package com.efishery.fetchapp.domain.service

import com.efishery.fetchapp.domain.entity.Storages
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest

@SpringBootTest
class AggregationServiceTest {
    @Autowired
    private val aggregationService: AggregationService? = null

    private var storages = Storages()
    private var storages2 = Storages()
    private var storages3 = Storages()
    private var storages4 = Storages()
    private var storagesList = mutableListOf<Storages?>()

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

        storages2.uuid = "a4ba246f-a1fd-4797-ab67-9a64eb3f3162"
        storages2.komoditas = "BAWAL"
        storages2.size = 180
        storages2.price = 200000
        storages2.timestamp = "1641016968903"
        storages2.areaProvinsi = "JAWA BARAT"
        storages2.areaKota = "BANDUNG"
        storages2.tglParsed = "2022-01-01T06:02:48"

        storages3.uuid = "a4ba246f-a1fd-4797-ab67-9a64eb3f3162"
        storages3.komoditas = "NILA"
        storages3.size = 200
        storages3.price = 95000
        storages3.timestamp = "1641016968903"
        storages3.areaProvinsi = "JAWA BARAT"
        storages3.areaKota = "BANDUNG"
        storages3.tglParsed = "2022-01-01T06:02:48"

        storages4.uuid = "a4ba246f-a1fd-4797-ab67-9a64eb3f3162"
        storages4.komoditas = "NILA"
        storages4.size = 200
        storages4.price = 95000
        storages4.timestamp = "1641016968903"
        storages4.areaProvinsi = "ACEH"
        storages4.areaKota = "BANDA ACEH"
        storages4.tglParsed = "2022-05-09T06:02:48"

        storagesList.add(storages)
        storagesList.add(storages2)
        storagesList.add(storages3)
        storagesList.add(storages4)
    }

    @Test
    fun weeklyGroupSizeTest() {
        val weeklyGroup = aggregationService?.weeklyGroup(storagesList)
        Assertions.assertEquals(2, weeklyGroup?.size)
    }

    @Test
    fun groupStorageByDateWeeklyTest() {
        val weeklyStorageGroup = aggregationService?.groupStorageByDateWeekly(storagesList)
        Assertions.assertEquals(2, weeklyStorageGroup?.size)
    }

    @Test
    fun groupStorageByProvinceTest() {
        val provinceGroup = aggregationService?.groupStorageByProvince(storagesList)
        Assertions.assertEquals(2, provinceGroup?.size)
    }
}
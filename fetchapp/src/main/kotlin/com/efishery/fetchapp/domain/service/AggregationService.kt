package com.efishery.fetchapp.domain.service

import com.efishery.fetchapp.domain.entity.Aggregation
import com.efishery.fetchapp.domain.entity.AggregationEntities
import com.efishery.fetchapp.domain.entity.AggregationData
import com.efishery.fetchapp.domain.entity.Storages
import org.springframework.stereotype.Service
import java.time.DayOfWeek
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.time.temporal.TemporalAdjusters


@Service
class AggregationService {
    fun weeklyGroup(storageList: MutableList<Storages?>): List<Aggregation> {
        val result = mutableListOf<Aggregation>()
        val grouping = groupingByProvinceThenByDateWeekly(storageList)
        grouping.forEach { provinceGrouping ->
            val aggregation = Aggregation()
            aggregation.province = provinceGrouping.key
            val dateAggregationDatumEntities = mutableListOf<AggregationEntities>()
            provinceGrouping.value.forEach { dateGrouping ->
                val aggregationEntities = AggregationEntities()

                val formatter: DateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd")
                aggregationEntities.startDateOfWeek = dateGrouping.key.format(formatter)

                val aggregationDataPrice = AggregationData()
                aggregationDataPrice.max = dateGrouping.value.maxBy { it!!.price!! }?.price
                aggregationDataPrice.min = dateGrouping.value.minBy { it!!.price!! }?.price
                aggregationDataPrice.avg = getAverage(dateGrouping.value.map { it!!.price!! })
                aggregationDataPrice.median = getMedian(dateGrouping.value.map { it!!.price!! })

                val aggregationDataSize = AggregationData()
                aggregationDataSize.max = dateGrouping.value.maxBy { it!!.size!! }?.size
                aggregationDataSize.min = dateGrouping.value.minBy { it!!.size!! }?.size
                aggregationDataSize.avg = getAverage(dateGrouping.value.map { it!!.size!! })
                aggregationDataSize.median = getMedian(dateGrouping.value.map { it!!.size!! })

                aggregationEntities.price = aggregationDataPrice
                aggregationEntities.size = aggregationDataSize

                dateAggregationDatumEntities.add(aggregationEntities)
            }
            aggregation.aggregations = dateAggregationDatumEntities
            result.add(aggregation)
        }
        return result
    }

    fun groupingByProvinceThenByDateWeekly(storageList: MutableList<Storages?>): Map<String?, Map<LocalDate, List<Storages?>>> {
        val result = HashMap<String?, Map<LocalDate, List<Storages?>>>()

        val provinceGrouping = groupStorageByProvince(storageList)
        provinceGrouping.forEach {
            val weeklyGrouping = groupStorageByDateWeekly(it.value)
            result[it.key] = weeklyGrouping
        }

        return result
    }

    fun groupStorageByDateWeekly(storageList: List<Storages?>): Map<LocalDate, List<Storages?>> {
        // Delete outlier data with tgl_parsed is null
        val filteredStorageList = storageList.stream().filter { it?.tglParsed != null }.toList().toMutableList()

        val dateGroupByWeek = filteredStorageList.groupBy {
            LocalDate.parse(it?.tglParsed!!.substring(0, it.tglParsed!!.indexOf('T')), DateTimeFormatter.ISO_DATE)
                .with(TemporalAdjusters.previousOrSame(DayOfWeek.MONDAY))
        }

        return dateGroupByWeek
    }

    fun groupStorageByProvince(storageList: List<Storages?>): Map<String?, List<Storages?>> {
        // Delete outlier data with area_province is null
        val filteredStorageList = storageList.stream().filter { it?.areaProvinsi != null }.toList().toMutableList()

        return filteredStorageList.groupBy { it?.areaProvinsi }
    }

    fun getAverage(listOfNumber: List<Int>): Double {
        val stats = listOfNumber.stream()
            .mapToInt { it }
            .summaryStatistics()
        return stats.average
    }

    fun getMedian(listOfNumber: List<Int>): Double {
        val sortedListOfNumber = listOfNumber.sorted()
        val midIndex = sortedListOfNumber.size / 2
        return if (sortedListOfNumber.size % 2 == 0)
            (sortedListOfNumber[midIndex - 1] + sortedListOfNumber[midIndex] / 2).toDouble()
        else sortedListOfNumber[midIndex].toDouble()
    }
}
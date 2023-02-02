package com.efishery.fetchapp.adapter.output.webclient.dto

data class StoragesWebDto (
    var uuid: String,
    var komoditas : String,
    var areaProvisi: String,
    var areaKota: String,
    var size: String,
    var price: String,
    var tglParsed: String,
    var timestamp: String
        )
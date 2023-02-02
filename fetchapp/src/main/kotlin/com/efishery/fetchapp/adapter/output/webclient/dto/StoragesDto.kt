package com.efishery.fetchapp.adapter.output.webclient.dto

import com.fasterxml.jackson.annotation.JsonProperty

data class StoragesDto (
    var uuid: String? = null,
    var komoditas : String? = null,
    @JsonProperty("area_provinsi")
    var areaProvinsi: String? = null,
    @JsonProperty("area_kota")
    var areaKota: String? = null,
    var size: String? = null,
    var price: String? = null,
    @JsonProperty("tgl_parsed")
    var tglParsed: String? = null,
    var timestamp: String? = null
)
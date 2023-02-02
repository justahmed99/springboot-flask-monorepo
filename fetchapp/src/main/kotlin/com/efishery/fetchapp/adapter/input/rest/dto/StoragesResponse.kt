package com.efishery.fetchapp.adapter.input.rest.dto

import com.fasterxml.jackson.annotation.JsonProperty

class StoragesResponse {
    var uuid: String? = null
    var komoditas: String? = null
    @JsonProperty("area_provinsi")
    var areaProvinsi: String? = null
    @JsonProperty("area_kota")
    var areaKota: String? = null
    var size: Int? = null
    var price: Int? = null
    @JsonProperty("tgl_parsed")
    var tglParsed: String? = null
    var timestamp: String? = null
}
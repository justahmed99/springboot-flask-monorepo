package com.efishery.fetchapp.adapter.output.webclient.dto

import com.efishery.fetchapp.adapter.output.webclient.dto.freecurrency.DataDTO
import com.fasterxml.jackson.annotation.JsonProperty

class FreeCurrencyAPIDTO {
    @JsonProperty("data")
    var data: DataDTO? = null
}
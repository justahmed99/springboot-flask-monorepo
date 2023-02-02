package com.efishery.fetchapp.adapter.output.webclient

import com.efishery.fetchapp.adapter.output.webclient.converter.StoragesConverter
import com.efishery.fetchapp.adapter.output.webclient.dto.FreeCurrencyAPIDTO
import com.efishery.fetchapp.adapter.output.webclient.dto.StoragesDto
import com.efishery.fetchapp.application.port.output.StoragesWebClient
import com.efishery.fetchapp.domain.entity.Dollar
import com.efishery.fetchapp.domain.entity.Storages
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import org.springframework.web.reactive.function.client.WebClient
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono


@Service
class StoragesWebClientAdapter: StoragesWebClient {

    @Autowired
    private final val storagesConverter: StoragesConverter? = null

    override fun getStorages(): Flux<Storages> {
        val client = WebClient.create("https://stein.efishery.com/v1")
        return client.get()
            .uri("/storages/5e1edf521073e315924ceab4/list")
            .retrieve()
            .bodyToFlux(StoragesDto::class.java)
            .mapNotNull { storagesConverter?.convertDTOtoEntityStoragesWeb(it) }
    }

    override fun getUSDValue(): Mono<Dollar> {
        val client = WebClient.create("https://api.freecurrencyapi.com/v1")
        return client.get()
            .uri("/latest?apikey=qzVKpVR2m9SLzqh3tOSzYgbaIAMJEd2Y7EfDyGG7&currencies=USD&base_currency=IDR")
            .retrieve()
            .bodyToMono(FreeCurrencyAPIDTO::class.java)
            .mapNotNull { storagesConverter?.convertFreeCurrencyDTOtoDollarEntity(it) }
    }
}
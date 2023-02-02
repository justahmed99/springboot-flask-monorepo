package com.efishery.fetchapp.adapter.output.webclient

import com.efishery.fetchapp.adapter.output.webclient.converter.StoragesWebConverter
import com.efishery.fetchapp.adapter.output.webclient.dto.StoragesWebDto
import com.efishery.fetchapp.application.port.output.StoragesWebWebClient
import com.efishery.fetchapp.domain.entity.StoragesWeb
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import org.springframework.web.reactive.function.client.WebClient
import reactor.core.publisher.Flux


@Service
class StoragesWebWebClientAdapter: StoragesWebWebClient {

    @Autowired
    private final val storagesWebConverter: StoragesWebConverter? = null

    override fun getStorages(): Flux<StoragesWeb> {
        val client = WebClient.create("https://stein.efishery.com/v1")
        return client.get()
            .uri("/storages/5e1edf521073e315924ceab4/list")
            .retrieve()
            .bodyToFlux(StoragesWebDto::class.java)
            .map { storagesWebConverter?.convertDTOtoEntityStoragesWeb(it) }
    }

}
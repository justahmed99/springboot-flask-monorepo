package com.efishery.fetchapp.application.port.output

import com.efishery.fetchapp.domain.entity.Dollar
import com.efishery.fetchapp.domain.entity.Storages
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono

interface StoragesWebClient {
    fun getStorages(): Flux<Storages>
    fun getUSDValue(): Mono<Dollar>
}
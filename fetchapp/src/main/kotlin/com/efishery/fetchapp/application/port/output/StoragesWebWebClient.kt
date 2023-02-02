package com.efishery.fetchapp.application.port.output

import com.efishery.fetchapp.domain.entity.StoragesWeb
import reactor.core.publisher.Flux

interface StoragesWebWebClient {
    fun getStorages(): Flux<StoragesWeb>
}
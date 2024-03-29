package com.efishery.fetchapp.application.usecase

import com.efishery.fetchapp.domain.entity.Dollar
import com.efishery.fetchapp.domain.entity.Storages
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono

interface StoragesWebCommand {
    fun getListStoragesWeb(): Flux<Storages>
    fun getDollar(): Mono<Dollar>
}
package com.efishery.fetchapp.application.usecase

import com.efishery.fetchapp.domain.entity.StoragesWeb
import reactor.core.publisher.Flux

interface StoragesWebCommand {
    fun getListStoragesWeb(): Flux<StoragesWeb>
}
package com.efishery.fetchapp.application.port.input

import com.efishery.fetchapp.application.port.output.StoragesWebClient
import com.efishery.fetchapp.application.usecase.StoragesWebCommand
import com.efishery.fetchapp.domain.entity.Dollar
import com.efishery.fetchapp.domain.entity.Storages
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono

@Service
class StoragesCommandImpl: StoragesWebCommand {

    @Autowired
    private final val storagesWebClient: StoragesWebClient? = null

    override fun getListStoragesWeb(): Flux<Storages> {
        return storagesWebClient!!.getStorages()
    }

    override fun getDollar(): Mono<Dollar> {
        return storagesWebClient!!.getUSDValue()
    }
}
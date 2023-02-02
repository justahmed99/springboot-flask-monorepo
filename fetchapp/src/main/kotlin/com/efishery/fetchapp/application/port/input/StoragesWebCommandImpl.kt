package com.efishery.fetchapp.application.port.input

import com.efishery.fetchapp.application.port.output.StoragesWebWebClient
import com.efishery.fetchapp.application.usecase.StoragesWebCommand
import com.efishery.fetchapp.domain.entity.StoragesWeb
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import reactor.core.publisher.Flux

@Service
class StoragesWebCommandImpl: StoragesWebCommand {

    @Autowired
    private final val storagesWebWebClient: StoragesWebWebClient? = null

    override fun getListStoragesWeb(): Flux<StoragesWeb> {
        return storagesWebWebClient!!.getStorages()
    }
}
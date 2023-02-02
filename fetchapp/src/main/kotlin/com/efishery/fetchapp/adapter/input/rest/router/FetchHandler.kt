package com.efishery.fetchapp.adapter.input.rest.router

import com.efishery.fetchapp.adapter.input.rest.converter.RestConverter
import com.efishery.fetchapp.application.usecase.StoragesWebCommand
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component
import org.springframework.web.reactive.function.server.ServerRequest
import org.springframework.web.reactive.function.server.ServerResponse

@Component("FetchHandler")
class FetchHandler {
    @Autowired
    private val command: StoragesWebCommand? = null

    @Autowired
    private val converter: RestConverter? = null

    fun getData(request: ServerRequest) = command!!.getListStoragesWeb()
        .mapNotNull { storage -> converter?.convertStoragesEntityToStoragesResponse(storage) }
        .collectList()
        .flatMap { listOfStoragesWeb -> ServerResponse.ok().bodyValue(listOfStoragesWeb) }

    fun getDollarValue(request: ServerRequest) = command!!.getDollar()
        .flatMap { dollar -> ServerResponse.ok().bodyValue(dollar) }
}
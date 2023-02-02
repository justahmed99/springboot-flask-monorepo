package com.efishery.fetchapp.adapter.input.rest

import com.efishery.fetchapp.application.usecase.StoragesWebCommand
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component
import org.springframework.web.reactive.function.server.ServerRequest
import org.springframework.web.reactive.function.server.ServerResponse
import reactor.core.publisher.Mono

@Component("FetchHandler")
class FetchHandler {
    @Autowired
    private val command: StoragesWebCommand? = null

    fun getData(request: ServerRequest?): Mono<ServerResponse?>? {
        return command?.getListStoragesWeb()?.collectList()?.flatMap { listOfStoragesWeb -> ServerResponse.ok().bodyValue(listOfStoragesWeb) }
    }
}
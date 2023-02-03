package com.efishery.fetchapp.adapter.input.rest.router

import com.efishery.fetchapp.adapter.input.rest.constant.MessageConstant
import com.efishery.fetchapp.adapter.input.rest.converter.RestConverter
import com.efishery.fetchapp.adapter.input.rest.dto.MessageResponse
import com.efishery.fetchapp.application.usecase.StoragesWebCommand
import com.efishery.fetchapp.adapter.input.rest.utils.JwtUtils
import com.efishery.fetchapp.domain.service.AggregationService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.stereotype.Component
import org.springframework.web.reactive.function.server.ServerRequest
import org.springframework.web.reactive.function.server.ServerResponse
import reactor.core.publisher.Mono

@Component("FetchHandler")
class FetchHandler {
    @Autowired
    private val command: StoragesWebCommand? = null

    @Autowired
    private val converter: RestConverter? = null

    @Autowired
    private val jwtUtils: JwtUtils? = null

    @Autowired
    private val aggregationService: AggregationService? = null

    fun getData(request: ServerRequest?): Mono<ServerResponse?> {
        return jwtUtils!!.verifyJwt(request!!)
            .flatMap { status ->
                if (status) {
                    command!!.getListStoragesWeb()
                        .mapNotNull { storage -> converter?.convertStoragesEntityToStoragesResponse(storage) }
                        .collectList()
                        .flatMap { storageListResponse -> ServerResponse.ok().bodyValue(storageListResponse) }
                } else {
                    ServerResponse.status(HttpStatus.UNAUTHORIZED)
                        .body(Mono.just(MessageResponse(MessageConstant.INVALID_TOKEN)), MessageResponse::class.java)
                }
            }
    }

    fun getDataConvertedToDollar(request: ServerRequest?): Mono<ServerResponse?> {
        return jwtUtils!!.verifyJwt(request!!)
            .flatMap { status ->
                if (status) {
                    command!!.getDollar()
                        .zipWith(
                            command.getListStoragesWeb()
                                .mapNotNull { storage -> converter?.convertStoragesEntityToStoragesResponse(storage) }
                                .collectList()
                        ) { dollar, storageList ->
                            converter?.addUSDValue(storageList, dollar.value!!)
                        }.flatMap { storageListResponse -> ServerResponse.ok().bodyValue(storageListResponse!!) }
                } else {
                    ServerResponse.status(HttpStatus.UNAUTHORIZED)
                        .body(Mono.just(MessageResponse(MessageConstant.INVALID_TOKEN)), MessageResponse::class.java)
                }
            }
    }

    fun getDataByWeekly(request: ServerRequest?): Mono<ServerResponse?> {
        return jwtUtils!!.verifyJwt(request!!)
            .flatMap { status ->
                if (jwtUtils.role(request) != "admin") {
                    return@flatMap ServerResponse.status(HttpStatus.UNAUTHORIZED)
                        .body(Mono.just(MessageResponse(MessageConstant.INVALID_ROLE)), MessageResponse::class.java)
                }
                if (status) {
                    command!!.getListStoragesWeb()
                        .collectList()
                        .flatMap { Mono.just(aggregationService!!.weeklyGroup(it)) }
                        .flatMap { ServerResponse.ok().bodyValue(it) }
                } else {
                    ServerResponse.status(HttpStatus.UNAUTHORIZED)
                        .body(Mono.just(MessageResponse(MessageConstant.INVALID_TOKEN)), MessageResponse::class.java)
                }
            }
    }

}
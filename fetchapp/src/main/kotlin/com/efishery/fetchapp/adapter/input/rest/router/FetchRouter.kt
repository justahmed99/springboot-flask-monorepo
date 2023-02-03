package com.efishery.fetchapp.adapter.input.rest.router

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.http.MediaType
import org.springframework.web.reactive.function.server.*

@Configuration("FetchRouter")
class FetchRouter {

    @Bean("fetchRouters")
    fun fetchRouters(handler: FetchHandler) = RouterFunctions.route(
        RequestPredicates.GET("/fetch-app/list").and(RequestPredicates.accept(MediaType.APPLICATION_JSON))
    ) {
        handler.getData(it)
    }.andRoute(
        RequestPredicates.GET("/fetch-app/list/dollar").and(RequestPredicates.accept(MediaType.APPLICATION_JSON))
    ) {
        handler.getDataConvertedToDollar(it)
    }.andRoute(
        RequestPredicates.GET("/fetch-app/list/by-weekly").and(RequestPredicates.accept(MediaType.APPLICATION_JSON))
    ) {
        handler.getDataByWeekly(it)
    }
}
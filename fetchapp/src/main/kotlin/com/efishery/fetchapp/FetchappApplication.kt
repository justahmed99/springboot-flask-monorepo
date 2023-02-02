package com.efishery.fetchapp

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.web.reactive.config.EnableWebFlux

@SpringBootApplication
@EnableWebFlux
class FetchappApplication

fun main(args: Array<String>) {
    runApplication<FetchappApplication>(*args)
}

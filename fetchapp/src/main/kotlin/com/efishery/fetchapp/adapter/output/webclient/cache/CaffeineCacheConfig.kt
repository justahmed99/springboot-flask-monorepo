package com.efishery.fetchapp.adapter.output.webclient.cache

import com.github.benmanes.caffeine.cache.Caffeine
import org.springframework.beans.factory.annotation.Value
import org.springframework.cache.CacheManager
import org.springframework.cache.caffeine.CaffeineCacheManager
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import java.util.concurrent.TimeUnit


@Configuration
class CaffeineCacheConfig {

    @Value("\${cache.duration-in-second}")
    private var duration: Long = 0L

    @Bean
    fun caffeineConfig(): Caffeine<Any?, Any?>? {
        return Caffeine.newBuilder()
            .expireAfterWrite(duration, TimeUnit.SECONDS)
            .initialCapacity(10)
    }

    @Bean
    fun cacheManager(caffeine: Caffeine<Any, Any>): CacheManager? {
        val caffeineCacheManager = CaffeineCacheManager()
        caffeineCacheManager.setCaffeine(caffeine)
        return caffeineCacheManager
    }
}
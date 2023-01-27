package com.example.testapprandomusers.repositories

import io.ktor.client.HttpClient
import io.ktor.client.engine.cio.CIO
import io.ktor.client.plugins.HttpRequestRetry
import io.ktor.client.plugins.HttpTimeout
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json
import org.koin.dsl.module


val clientModule = module {
    factory {
        HttpClient(CIO) {
            install(HttpRequestRetry) {
                retryOnServerErrors(maxRetries = 5)
                exponentialDelay()
            }
            install(HttpTimeout) {
                requestTimeoutMillis = 60000
            }
            install(ContentNegotiation) {
                json(getKoin().get())
            }
        }
    }
    single {
        Json {
            prettyPrint = true
            isLenient = true
            ignoreUnknownKeys = true
            encodeDefaults = true
        }
    }
}

/*
val clientModule = module {
    factory {
        HttpClient(CIO) {
            install(HttpRequestRetry) {
                retryOnServerErrors(maxRetries = 5)
                exponentialDelay()
            }
            install(Logging) {
                logger = Logger.ANDROID
                level = LogLevel.ALL
            }
            install(ContentNegotiation) {
                json(get())
            }
        }
    }
    single {
        Json {
            prettyPrint = true
            isLenient = true
            ignoreUnknownKeys = true
            encodeDefaults = true
        }
    }
}*/

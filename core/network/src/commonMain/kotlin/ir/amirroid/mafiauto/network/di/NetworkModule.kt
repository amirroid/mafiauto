package ir.amirroid.mafiauto.network.di

import io.ktor.client.HttpClient
import io.ktor.client.HttpClientConfig
import io.ktor.client.plugins.HttpTimeout
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.defaultRequest
import io.ktor.client.request.header
import io.ktor.http.ContentType
import io.ktor.http.contentType
import io.ktor.serialization.kotlinx.json.json
import ir.amirroid.mafiauto.common.app.utils.AppInfo
import ir.amirroid.mafiauto.network.API_CONNECT_TIMEOUT_SECONDS
import ir.amirroid.mafiauto.network.API_REQUEST_TIMEOUT_MILLIS
import ir.amirroid.mafiauto.network.API_SOCKET_TIMEOUT_SECONDS
import ir.amirroid.mafiauto.network.client.createHttpClientProvider
import kotlinx.serialization.json.Json
import org.koin.dsl.module

val networkModule = module {
    single<HttpClient> { createConfiguredClient(get()) }
}

private fun HttpClientConfig<*>.configureContentNegotiation(json: Json) {
    install(ContentNegotiation) {
        json(json)
    }
}

private fun HttpClientConfig<*>.configureTimeout() {
    install(HttpTimeout) {
        requestTimeoutMillis = API_REQUEST_TIMEOUT_MILLIS
        connectTimeoutMillis = API_CONNECT_TIMEOUT_SECONDS
        socketTimeoutMillis = API_SOCKET_TIMEOUT_SECONDS
    }
}


private fun HttpClientConfig<*>.configureDefaultRequest() {
    defaultRequest {
        contentType(ContentType.Application.Json)
        header("User-Agent", "${AppInfo.appName}/${AppInfo.version}")
    }
}

private fun createConfiguredClient(
    json: Json
): HttpClient {
    return createHttpClientProvider().provide().config {
        configureContentNegotiation(json)
        configureTimeout()
        configureDefaultRequest()
    }
}
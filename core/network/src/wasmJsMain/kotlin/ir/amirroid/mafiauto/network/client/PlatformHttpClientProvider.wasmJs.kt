package ir.amirroid.mafiauto.network.client

import io.ktor.client.HttpClient
import io.ktor.client.engine.js.Js


class WebHttpClientProvider : PlatformHttpClientProvider {
    override fun provide(): HttpClient {
        return HttpClient(Js)
    }
}

actual fun createHttpClientProvider(): PlatformHttpClientProvider {
    return WebHttpClientProvider()
}
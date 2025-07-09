package ir.amirroid.mafiauto.network.client

import io.ktor.client.HttpClient
import io.ktor.client.engine.darwin.Darwin

class IosHttpClientProvider : PlatformHttpClientProvider {
    override fun provide(): HttpClient {
        return HttpClient(Darwin)
    }
}

actual fun createHttpClientProvider(): PlatformHttpClientProvider {
    return IosHttpClientProvider()
}
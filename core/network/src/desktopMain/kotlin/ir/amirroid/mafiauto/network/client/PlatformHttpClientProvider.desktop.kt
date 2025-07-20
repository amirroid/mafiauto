package ir.amirroid.mafiauto.network.client

import io.ktor.client.HttpClient
import io.ktor.client.engine.okhttp.OkHttp
import ir.amirroid.mafiauto.network.API_RETRY_ON_CONNECTION_FAILURE

class DesktopHttpClientProvider : PlatformHttpClientProvider {
    override fun provide(): HttpClient {
        return HttpClient(OkHttp) {
            engine {
                config {
                    retryOnConnectionFailure(API_RETRY_ON_CONNECTION_FAILURE)
                }
            }
        }
    }
}

actual fun createHttpClientProvider(): PlatformHttpClientProvider {
    return DesktopHttpClientProvider()
}
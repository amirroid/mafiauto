package ir.amirroid.mafiauto.network.call

import io.ktor.client.call.body
import io.ktor.client.plugins.ClientRequestException
import io.ktor.client.plugins.HttpRequestTimeoutException
import io.ktor.client.plugins.ServerResponseException
import io.ktor.client.statement.HttpResponse
import io.ktor.serialization.JsonConvertException
import io.ktor.util.network.UnresolvedAddressException
import ir.amirroid.mafiauto.common.app.response.Response
import ir.amirroid.mafiauto.network.response.NetworkErrors
import kotlinx.io.IOException
import kotlinx.serialization.SerializationException

object SafeApiCall {
    suspend inline fun <reified D> launch(
        action: () -> HttpResponse
    ): Response<D, NetworkErrors> {
        val response = try {
            action.invoke()
        } catch (_: JsonConvertException) {
            return Response.Error(NetworkErrors.Serialize)
        } catch (_: UnresolvedAddressException) {
            return Response.Error(NetworkErrors.NoInternet)
        } catch (_: SerializationException) {
            return Response.Error(NetworkErrors.Serialize)
        } catch (_: ServerResponseException) {
            return Response.Error(NetworkErrors.ServerError)
        } catch (_: ClientRequestException) {
            return Response.Error(NetworkErrors.ClientError)
        } catch (_: IOException) {
            return Response.Error(NetworkErrors.NoInternet)
        } catch (_: HttpRequestTimeoutException) {
            return Response.Error(NetworkErrors.RequestTimeout)
        } catch (_: Exception) {
            return Response.Error(NetworkErrors.Unknown)
        }
        return when (response.status.value) {
            in 200..299 -> response.trySerialize()
            401 -> Response.Error(NetworkErrors.Unauthorized)
            404 -> Response.Error(NetworkErrors.NotFound)
            400 -> Response.Error(NetworkErrors.BadRequest)
            408 -> Response.Error(NetworkErrors.RequestTimeout)
            413 -> Response.Error(NetworkErrors.PayloadTooLarge)
            in 500..599 -> Response.Error(NetworkErrors.ServerError)
            else -> Response.Error(NetworkErrors.Unknown)
        }
    }

    suspend inline fun <reified D> HttpResponse.trySerialize(): Response<D, NetworkErrors> = try {
        val serialized = body<D>()
        Response.Success(serialized)
    } catch (_: SerializationException) {
        Response.Error(NetworkErrors.Serialize)
    } catch (_: Exception) {
        Response.Error(NetworkErrors.Unknown)
    }
}
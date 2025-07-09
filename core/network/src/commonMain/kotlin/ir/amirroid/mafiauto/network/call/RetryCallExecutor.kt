package ir.amirroid.mafiauto.network.call

import ir.amirroid.mafiauto.network.response.NetworkErrors
import ir.amirroid.mafiauto.common.app.response.Response
import ir.amirroid.mafiauto.common.app.response.onError
import ir.amirroid.mafiauto.common.app.response.onSuccess

object RetryCallExecutor {
    suspend fun <D> call(
        times: Int = 3,
        block: suspend () -> Response<D, NetworkErrors>
    ): Response<D, NetworkErrors> {
        var currentAttempt = 0
        var lastData: Response<D, NetworkErrors> = Response.Loading

        while (currentAttempt < times) {
            val data = block()
            lastData = data.onSuccess {
                return Response.Success(it)
            }.onError {
                currentAttempt++
            }
        }

        return lastData
    }
}
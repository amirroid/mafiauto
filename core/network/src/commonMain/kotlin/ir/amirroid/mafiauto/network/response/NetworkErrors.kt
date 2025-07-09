package ir.amirroid.mafiauto.network.response

import ir.amirroid.mafiauto.common.app.error.ErrorI


sealed class NetworkErrors : ErrorI {
    data object RequestTimeout : NetworkErrors()
    data object PayloadTooLarge : NetworkErrors()
    data object Unauthorized : NetworkErrors()
    data object NotFound : NetworkErrors()
    data object Serialize : NetworkErrors()
    data object NoInternet : NetworkErrors()
    data object Unknown : NetworkErrors()
    data object ServerError : NetworkErrors()
    data object ClientError : NetworkErrors()
    data object BadRequest : NetworkErrors()
}
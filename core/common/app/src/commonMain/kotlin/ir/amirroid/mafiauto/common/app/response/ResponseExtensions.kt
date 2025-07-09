package ir.amirroid.mafiauto.common.app.response

import ir.amirroid.mafiauto.common.app.error.ErrorI

inline fun <D, E : ErrorI, R> Response<D, E>.map(action: (D) -> R): Response<R, E> {
    return when (this) {
        is Response.Success -> Response.Success(action(data))
        is Response.Error -> Response.Error(error)
        Response.Loading -> Response.Loading
        else -> Response.Idle
    }
}

inline fun <D, E : ErrorI, R> Response<List<D>, E>.mapList(action: (D) -> R): Response<List<R>, E> {
    return when (this) {
        is Response.Success -> Response.Success(data.map(action))
        is Response.Error -> Response.Error(error)
        Response.Loading -> Response.Loading
        else -> Response.Idle
    }
}

inline fun <D, E : ErrorI> Response<D, E>.onSuccess(action: (D) -> Unit): Response<D, E> {
    return when (this) {
        is Response.Success -> {
            action(data)
            this
        }

        else -> this
    }
}


inline fun <D, E : ErrorI> Response<D, E>.onNotSuccess(action: () -> Unit): Response<D, E> {
    return when (this) {
        !is Response.Success -> {
            action()
            this
        }

        else -> this
    }
}

inline fun <D, E : ErrorI> Response<D, E>.onError(action: (E) -> Unit): Response<D, E> {
    return when (this) {
        is Response.Error -> {
            action(error)
            this
        }

        else -> this
    }
}

inline fun <D, E : ErrorI> Response<D, E>.onLoading(action: () -> Unit): Response<D, E> {
    return when (this) {
        is Response.Loading -> {
            action()
            this
        }

        else -> this
    }
}
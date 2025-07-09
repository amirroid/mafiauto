package ir.amirroid.mafiauto.ui_models.error

import ir.amirroid.mafiauto.common.app.error.ErrorI
import ir.amirroid.mafiauto.common.app.response.Response

fun <D> Response<D, ErrorI>.mapErrors(): Response<D, ErrorUiModel> {
    return when (this) {
        is Response.Error -> Response.Error(error.toUiModel())
        is Response.Success -> Response.Success(data)
        Response.Loading -> Response.Loading
        else -> Response.Idle
    }
}
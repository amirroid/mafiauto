package ir.amirroid.mafiauto.ui_models.error

import ir.amirroid.mafiauto.common.app.error.ErrorI
import ir.amirroid.mafiauto.network.response.NetworkErrors

fun ErrorI.toUiModel(): ErrorUiModel {
    return when (this) {
        is NetworkErrors -> toUiModel()
        else -> ErrorUiModel.Unknown
    }
}

private fun NetworkErrors.toUiModel() = when (this) {
    is NetworkErrors.RequestTimeout -> ErrorUiModel.Network.Timeout
    else -> ErrorUiModel.Network.Default
}
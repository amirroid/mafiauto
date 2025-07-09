package ir.amirroid.mafiauto.ui_models.error

import ir.amirroid.mafiauto.common.app.error.ErrorI
import ir.amirroid.mafiauto.resources.Resources
import org.jetbrains.compose.resources.StringResource

sealed interface ErrorUiModel : ErrorI {
    val message: StringResource

    sealed interface Network : ErrorUiModel {
        data object Default : Network {
            override val message: StringResource = Resources.strings.defaultNetworkError
        }

        data object Timeout : Network {
            override val message: StringResource = Resources.strings.timeoutNetworkError
        }
    }

    data object Unknown : ErrorUiModel {
        override val message: StringResource = Resources.strings.unknownError
    }
}
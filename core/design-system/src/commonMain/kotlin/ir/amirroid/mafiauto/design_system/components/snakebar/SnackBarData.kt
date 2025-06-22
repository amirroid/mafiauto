package ir.amirroid.mafiauto.design_system.components.snakebar

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import ir.amirroid.mafiauto.resources.Resources
import org.jetbrains.compose.resources.StringResource
import org.jetbrains.compose.resources.stringResource
import kotlin.random.Random

sealed interface SnackBarData {
    val key: String
    var visible: Boolean
    val type: SnackBaType

    @Composable
    fun rememberText(): String

    data class Resource(
        val text: StringResource,
        override val type: SnackBaType
    ) : SnackBarData {
        override val key: String = text.key + Random.nextInt()
        override var visible by mutableStateOf(true)

        @Composable
        override fun rememberText(): String {
            return stringResource(text)
        }
    }

    data class Text(
        val text: String,
        override val type: SnackBaType
    ) : SnackBarData {
        override val key: String = text + Random.nextInt()
        override var visible by mutableStateOf(true)

        @Composable
        override fun rememberText(): String = text
    }
}

enum class SnackBaType(val displayName: StringResource) {
    WARNING(Resources.strings.warning),
    INFO(Resources.strings.info)
}
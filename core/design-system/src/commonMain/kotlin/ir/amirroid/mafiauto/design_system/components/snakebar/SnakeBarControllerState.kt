package ir.amirroid.mafiauto.design_system.components.snakebar

import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.Stable
import androidx.compose.runtime.remember
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.hapticfeedback.HapticFeedback
import androidx.compose.ui.hapticfeedback.HapticFeedbackType
import androidx.compose.ui.platform.LocalHapticFeedback
import kotlinx.coroutines.Job
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import org.jetbrains.compose.resources.StringResource

@Stable
class SnakeBarControllerState(
    private val hapticFeedback: HapticFeedback
) {
    private val _snacks = MutableStateFlow(emptyList<SnackBarData>())
    internal val snacks = _snacks.asStateFlow()

    private var lastJob: Job? = null

    suspend fun show(
        text: StringResource,
        time: Long = 4000
    ) = coroutineScope {
        lastJob?.cancel()
        lastJob = launch {
            hapticFeedback.performHapticFeedback(HapticFeedbackType.LongPress)
            _snacks.update { oldData ->
                oldData.forEach { it.visible = false }
                oldData + SnackBarData(text)
            }
            delay(time)
            _snacks.value.forEach { it.visible = false }
        }
    }
}

@Composable
fun rememberSnakeBarControllerState(): SnakeBarControllerState {
    val hapticFeedback = LocalHapticFeedback.current
    return remember {
        SnakeBarControllerState(hapticFeedback)
    }
}

val LocalSnakeBarControllerState =
    staticCompositionLocalOf<SnakeBarControllerState> { error("SnakeBarControllerState not provided") }


@Composable
fun WithSnakeBarControllerState(
    state: SnakeBarControllerState = rememberSnakeBarControllerState(),
    content: @Composable () -> Unit
) {
    CompositionLocalProvider(
        LocalSnakeBarControllerState provides state,
        content = content
    )
}
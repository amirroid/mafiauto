package ir.amirroid.mafiauto.design_system.components.snakebar

import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.Stable
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.hapticfeedback.HapticFeedback
import androidx.compose.ui.hapticfeedback.HapticFeedbackType
import androidx.compose.ui.platform.LocalHapticFeedback
import ir.amirroid.mafiauto.common.app.utils.emptyImmutableList
import kotlinx.collections.immutable.toImmutableList
import kotlinx.coroutines.CoroutineScope
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
    private val hapticFeedback: HapticFeedback,
    private val scope: CoroutineScope
) {
    private val _snacks = MutableStateFlow(emptyImmutableList<SnackBarData>())
    internal val snacks = _snacks.asStateFlow()

    private var lastJob: Job? = null

    fun show(
        text: StringResource,
        type: SnackBaType = SnackBaType.WARNING,
        formatArgs: List<Any> = emptyList(),
        time: Long = 4000
    ) {
        lastJob?.cancel()
        lastJob = scope.launch {
            showAndWait(text, type, formatArgs, time, withCancelLastJob = false)
        }
    }

    suspend fun showAndWait(
        text: StringResource,
        type: SnackBaType = SnackBaType.WARNING,
        formatArgs: List<Any> = emptyList(),
        time: Long = 4000,
        withCancelLastJob: Boolean = true
    ) {
        if (withCancelLastJob) {
            lastJob?.cancel()
        }
        hapticFeedback.performHapticFeedback(HapticFeedbackType.LongPress)
        val isAnySnakeBarVisible = _snacks.value.any { it.visible }
        _snacks.value.forEach { it.visible = false }
        if (isAnySnakeBarVisible) {
            delay(200)
        }
        _snacks.update { oldData ->
            (oldData + SnackBarData.Resource(
                text,
                type,
                formatArgs = formatArgs
            )).toImmutableList()
        }
        delay(time)
        _snacks.value.forEach { it.visible = false }
    }


    fun show(
        text: String,
        type: SnackBaType = SnackBaType.WARNING,
        time: Long = 4000
    ) {
        lastJob?.cancel()
        lastJob = scope.launch {
            hapticFeedback.performHapticFeedback(HapticFeedbackType.LongPress)
            val isAnySnakeBarVisible = _snacks.value.any { it.visible }
            _snacks.value.forEach { it.visible = false }
            if (isAnySnakeBarVisible) {
                delay(200)
            }
            _snacks.update { oldData ->
                (oldData + SnackBarData.Text(
                    text,
                    type
                )).toImmutableList()
            }
            delay(time)
            _snacks.value.forEach { it.visible = false }
        }
    }
}

@Composable
fun rememberSnakeBarControllerState(): SnakeBarControllerState {
    val hapticFeedback = LocalHapticFeedback.current
    val scope = rememberCoroutineScope()
    return remember {
        SnakeBarControllerState(hapticFeedback, scope)
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
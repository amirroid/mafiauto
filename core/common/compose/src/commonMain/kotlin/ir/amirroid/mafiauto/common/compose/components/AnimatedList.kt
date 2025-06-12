package ir.amirroid.mafiauto.common.compose.components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.EnterTransition
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.spring
import androidx.compose.animation.fadeIn
import androidx.compose.animation.slideInVertically
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.key
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import kotlinx.coroutines.delay

@Composable
fun <T> AnimatedList(
    items: List<T>,
    modifier: Modifier = Modifier,
    delayPerItem: Long = 50L,
    enter: EnterTransition = fadeIn(
        animationSpec = spring(
            stiffness = Spring.StiffnessLow,
            dampingRatio = Spring.DampingRatioNoBouncy
        )
    ) + slideInVertically(
        initialOffsetY = { it / 2 },
        animationSpec = spring(
            stiffness = Spring.StiffnessMediumLow,
            dampingRatio = Spring.DampingRatioMediumBouncy
        )
    ),
    justItemVisible: Int? = null,
    itemKey: ((index: Int, item: T) -> Any)? = null,
    content: @Composable (index: Int, item: T) -> Unit
) {
    val visibleStates = remember(items) { items.map { mutableStateOf(false) } }

    LaunchedEffect(items) {
        visibleStates.forEachIndexed { index, state ->
            delay(delayPerItem * index)
            state.value = true
        }
    }

    Column(modifier = modifier.verticalScroll(rememberScrollState())) {
        items.forEachIndexed { index, item ->
            val key = itemKey?.invoke(index, item) ?: index
            key(key) {
                AnimatedVisibility(
                    visible = visibleStates[index].value && (justItemVisible == index || justItemVisible == null),
                    enter = enter
                ) {
                    content(index, item)
                }
            }
        }
    }
}
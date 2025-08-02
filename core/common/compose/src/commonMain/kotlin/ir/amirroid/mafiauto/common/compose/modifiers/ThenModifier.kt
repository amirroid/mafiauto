package ir.amirroid.mafiauto.common.compose.modifiers

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

@Composable
fun Modifier.thenIf(condition: Boolean, action: @Composable Modifier.() -> Modifier): Modifier {
    return if (condition) {
        this.action()
    } else {
        this
    }
}

@Composable
fun <D> Modifier.thenIfNotNull(obj: D?, action: @Composable Modifier.(D) -> Modifier): Modifier {
    return thenIf(obj != null) {
        action(obj!!)
    }
}
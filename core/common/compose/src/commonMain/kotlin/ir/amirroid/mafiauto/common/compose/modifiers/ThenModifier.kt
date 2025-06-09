package ir.amirroid.mafiauto.common.compose.modifiers

import androidx.compose.ui.Modifier

fun Modifier.thenIf(condition: Boolean, action: Modifier.() -> Modifier): Modifier {
    return if (condition) {
        this.action()
    } else {
        this
    }
}

fun <D> Modifier.thenIfNotNull(obj: D?, action: Modifier.(D) -> Modifier): Modifier {
    return thenIf(obj != null) {
        action(obj!!)
    }
}
package ir.amirroid.mafiauto.common.compose.modifiers

import androidx.compose.ui.Modifier
import androidx.compose.ui.input.pointer.PointerEventType
import androidx.compose.ui.input.pointer.isSecondaryPressed
import androidx.compose.ui.input.pointer.pointerInput

fun Modifier.onSecondaryClick(onClick: () -> Unit) = pointerInput(Unit) {
    while (true) {
        awaitPointerEventScope {
            val event = awaitPointerEvent()
            if (event.type == PointerEventType.Press && event.buttons.isSecondaryPressed) {
                onClick()
                event.changes.forEach { it.consume() }
            }
        }
    }
}
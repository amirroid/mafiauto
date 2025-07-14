package ir.amirroid.mafiauto.common.compose.extensions

import androidx.compose.ui.focus.FocusDirection
import androidx.compose.ui.focus.FocusDirection.Companion.Enter
import androidx.compose.ui.focus.FocusRequester

fun FocusRequester.requestFocusCaching(focusDirection: FocusDirection = Enter) = runCatching {
    requestFocus(focusDirection)
}
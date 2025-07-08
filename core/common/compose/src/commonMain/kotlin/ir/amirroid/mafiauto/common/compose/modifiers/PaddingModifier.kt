package ir.amirroid.mafiauto.common.compose.modifiers

import androidx.compose.foundation.layout.padding
import androidx.compose.ui.Modifier
import ir.amirroid.mafiauto.theme.core.AppTheme

private val horizontalPadding = AppTheme.horizontalPadding
private val verticalPadding = AppTheme.verticalPadding

fun Modifier.allPadding() = padding(horizontal = horizontalPadding, vertical = verticalPadding)

fun Modifier.horizontalPadding() = padding(horizontal = horizontalPadding)
fun Modifier.verticalPadding() = padding(vertical = verticalPadding)

fun Modifier.topPadding() = padding(top = verticalPadding)
fun Modifier.bottomPadding() = padding(bottom = verticalPadding)
fun Modifier.startPadding() = padding(start = horizontalPadding)
fun Modifier.endPadding() = padding(end = horizontalPadding)
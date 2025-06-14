package ir.amirroid.mafiauto.common.compose.extra

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.ime
import androidx.compose.foundation.layout.systemBars
import androidx.compose.runtime.Composable
import ir.amirroid.mafiauto.common.compose.operators.plus
import ir.amirroid.mafiauto.design_system.core.AppTheme

private val horizontalPadding = AppTheme.horizontalPadding
private val verticalPadding = AppTheme.verticalPadding

@Composable
fun defaultContentPadding() =
    PaddingValues(
        horizontal = horizontalPadding,
        vertical = verticalPadding
    ) + WindowInsets.systemBars.asPaddingValues() + WindowInsets.ime.asPaddingValues()

@Composable
fun defaultHorizontalContentPadding() =
    PaddingValues(
        horizontal = horizontalPadding,
    )
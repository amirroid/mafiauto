package ir.amirroid.mafiauto.design_system.components.field

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.Dp
import ir.amirroid.mafiauto.design_system.components.surface.MSurface
import ir.amirroid.mafiauto.theme.locales.LocalContentColor
import ir.amirroid.mafiauto.theme.locales.LocalTextStyle

@Composable
fun MTextField(
    value: String,
    onValueChange: (String) -> Unit,
    modifier: Modifier = Modifier,
    placeholder: (@Composable () -> Unit)? = null,
    minHeight: Dp = TextFieldsDefaults.minHeight,
    colors: TextFieldColors = TextFieldsDefaults.filledTextFieldColors,
    interactionSource: MutableInteractionSource? = null,
    borderWidth: Dp = TextFieldsDefaults.defaultBorderWidth,
    contentPadding: PaddingValues = TextFieldsDefaults.defaultContentPadding,
    shape: Shape = TextFieldsDefaults.defaultShape,
    singleLine: Boolean = false,
    maxLines: Int = if (singleLine) 1 else Int.MAX_VALUE,
    minLines: Int = 1,
    keyboardOptions: KeyboardOptions = KeyboardOptions.Default,
    keyboardActions: KeyboardActions = KeyboardActions.Default,
) {
    BasicTextField(
        value = value,
        onValueChange = onValueChange,
        interactionSource = interactionSource,
        singleLine = singleLine,
        maxLines = maxLines,
        minLines = minLines,
        modifier = modifier.heightIn(min = minHeight),
        textStyle = LocalTextStyle.current.copy(color = colors.contentColor),
        cursorBrush = SolidColor(colors.cursorColor),
        keyboardOptions = keyboardOptions,
        keyboardActions = keyboardActions,
        decorationBox = { content ->
            MSurface(
                modifier = Modifier.fillMaxWidth(),
                color = colors.containerColor,
                contentColor = colors.contentColor,
                border = BorderStroke(borderWidth, colors.borderColor),
                shape = shape
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth().padding(contentPadding),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Box {
                        if (placeholder != null && value.isEmpty()) {
                            CompositionLocalProvider(
                                LocalContentColor provides colors.placeholderColor
                            ) {
                                placeholder.invoke()
                            }
                        }
                        content.invoke()
                    }
                }
            }
        }
    )
}

@Composable
fun MTextField(
    value: TextFieldValue,
    onValueChange: (TextFieldValue) -> Unit,
    modifier: Modifier = Modifier,
    placeholder: (@Composable () -> Unit)? = null,
    minHeight: Dp = TextFieldsDefaults.minHeight,
    colors: TextFieldColors = TextFieldsDefaults.filledTextFieldColors,
    interactionSource: MutableInteractionSource? = null,
    borderWidth: Dp = TextFieldsDefaults.defaultBorderWidth,
    contentPadding: PaddingValues = TextFieldsDefaults.defaultContentPadding,
    shape: Shape = TextFieldsDefaults.defaultShape,
    singleLine: Boolean = false,
    maxLines: Int = if (singleLine) 1 else Int.MAX_VALUE,
    minLines: Int = 1,
    keyboardOptions: KeyboardOptions = KeyboardOptions.Default,
    keyboardActions: KeyboardActions = KeyboardActions.Default,
) {
    BasicTextField(
        value = value,
        onValueChange = onValueChange,
        interactionSource = interactionSource,
        singleLine = singleLine,
        maxLines = maxLines,
        minLines = minLines,
        modifier = modifier.heightIn(min = minHeight),
        textStyle = LocalTextStyle.current.copy(color = colors.contentColor),
        cursorBrush = SolidColor(colors.cursorColor),
        keyboardActions = keyboardActions,
        keyboardOptions = keyboardOptions,
        decorationBox = { content ->
            MSurface(
                modifier = Modifier.fillMaxWidth(),
                color = colors.containerColor,
                contentColor = colors.contentColor,
                border = BorderStroke(borderWidth, colors.borderColor),
                shape = shape
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth().padding(contentPadding),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Box {
                        if (placeholder != null && value.text.isEmpty()) {
                            CompositionLocalProvider(
                                LocalContentColor provides colors.placeholderColor
                            ) {
                                placeholder.invoke()
                            }
                        }
                        content.invoke()
                    }
                }
            }
        }
    )
}
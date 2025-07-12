package ir.amirroid.mafiauto.common.compose.components

import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp
import ir.amirroid.mafiauto.design_system.components.text.MText
import ir.amirroid.mafiauto.theme.core.AppTheme
import ir.amirroid.mafiauto.resources.Resources
import org.jetbrains.compose.resources.stringResource

@Composable
fun InfoText(text: String, modifier: Modifier = Modifier) {
    val primaryColor = AppTheme.colorScheme.primary

    MText(
        buildAnnotatedString {
            withStyle(SpanStyle(fontWeight = FontWeight.Bold)) {
                append(stringResource(Resources.strings.info))
                append(": ")
            }
            append(text)
        },
        modifier = modifier
            .drawBehind {
                val strokeWidth = 2.dp.toPx()
                if (layoutDirection == LayoutDirection.Ltr) {
                    drawLine(
                        primaryColor,
                        start = Offset.Zero,
                        end = Offset(0f, size.height),
                        strokeWidth = strokeWidth,
                        cap = StrokeCap.Round
                    )
                } else {
                    drawLine(
                        primaryColor,
                        start = Offset(size.width - strokeWidth.div(2), 0f),
                        end = Offset(size.width - strokeWidth.div(2), size.height),
                        strokeWidth = strokeWidth,
                        cap = StrokeCap.Round
                    )
                }
            }
            .padding(start = 12.dp)
            .padding(vertical = 4.dp)
    )
}

@Composable
fun InfoText(text: AnnotatedString, modifier: Modifier = Modifier) {
    val primaryColor = AppTheme.colorScheme.primary
    MText(
        buildAnnotatedString {
            withStyle(SpanStyle(fontWeight = FontWeight.Bold)) {
                append(stringResource(Resources.strings.info))
                append(": ")
            }
            append(text)
        },
        modifier = modifier
            .drawBehind {
                val strokeWidth = 2.dp.toPx()
                if (layoutDirection == LayoutDirection.Ltr) {
                    drawLine(
                        primaryColor,
                        start = Offset.Zero,
                        end = Offset(0f, size.height),
                        strokeWidth = strokeWidth,
                        cap = StrokeCap.Round
                    )
                } else {
                    drawLine(
                        primaryColor,
                        start = Offset(size.width - strokeWidth.div(2), 0f),
                        end = Offset(size.width - strokeWidth.div(2), size.height),
                        strokeWidth = strokeWidth,
                        cap = StrokeCap.Round
                    )
                }
            }
            .padding(start = 12.dp)
            .padding(vertical = 4.dp)
    )
}
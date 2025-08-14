package ir.amirroid.mafiauto.guide.components

import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.rotate
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.util.lerp
import ir.amirroid.mafiauto.theme.locales.LocalContentColor

@Composable
fun ExpandToggleIcon(
    expanded: Boolean,
    modifier: Modifier = Modifier,
    iconSize: Dp = 20.dp,
    tint: Color = LocalContentColor.current,
    thickness: Dp = 2.dp
) {
    val progress by animateFloatAsState(
        targetValue = if (expanded) 1f else 0f,
        animationSpec = tween(durationMillis = 100, easing = LinearEasing),
        label = "ZoomAnimation"
    )

    Canvas(modifier = modifier.size(iconSize)) {
        val arrowLength = size.minDimension / 3f
        val arrowThickness = thickness.toPx()
        val center = size.minDimension / 2f

        val maxSpread = arrowLength * 1f
        val offset = lerp(
            start = -arrowLength / 2,
            stop = maxSpread,
            fraction = progress
        )

        rotate(-45f) {
            drawLine(
                color = tint,
                strokeWidth = arrowThickness,
                start = Offset(center - arrowLength / 2 + offset, center),
                end = Offset(center - arrowLength / 2 + offset + 10, center - 10),
                cap = StrokeCap.Round
            )
            drawLine(
                color = tint,
                strokeWidth = arrowThickness,
                start = Offset(center - arrowLength / 2 + offset, center),
                end = Offset(center - arrowLength / 2 + offset + 10, center + 10),
                cap = StrokeCap.Round
            )

            drawLine(
                color = tint,
                strokeWidth = arrowThickness,
                start = Offset(center + arrowLength / 2 - offset, center),
                end = Offset(center + arrowLength / 2 - offset - 10, center - 10),
                cap = StrokeCap.Round
            )
            drawLine(
                color = tint,
                strokeWidth = arrowThickness,
                start = Offset(center + arrowLength / 2 - offset, center),
                end = Offset(center + arrowLength / 2 - offset - 10, center + 10),
                cap = StrokeCap.Round
            )
        }
    }
}
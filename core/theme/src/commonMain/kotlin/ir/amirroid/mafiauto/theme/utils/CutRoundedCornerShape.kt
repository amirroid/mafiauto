package ir.amirroid.mafiauto.theme.utils

import androidx.compose.foundation.shape.CornerBasedShape
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.ui.geometry.Rect
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.geometry.toRect
import androidx.compose.ui.graphics.Outline
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp

class CutRoundedCornerShape(
    topStart: CornerSize,
    topEnd: CornerSize,
    bottomEnd: CornerSize,
    bottomStart: CornerSize
) : CornerBasedShape(
    topStart = topStart,
    topEnd = topEnd,
    bottomEnd = bottomEnd,
    bottomStart = bottomStart
) {
    override fun createOutline(
        size: Size,
        topStart: Float,
        topEnd: Float,
        bottomEnd: Float,
        bottomStart: Float,
        layoutDirection: LayoutDirection
    ): Outline {
        return if (topStart + topEnd + bottomStart + bottomEnd == 0f) {
            Outline.Rectangle(size.toRect())
        } else {
            Outline.Generic(Path().apply {
                reset()

                val width = size.width
                val height = size.height

                val topLeft = if (layoutDirection == LayoutDirection.Ltr) topStart else topEnd
                val topRight = if (layoutDirection == LayoutDirection.Ltr) topEnd else topStart
                val bottomRight =
                    if (layoutDirection == LayoutDirection.Ltr) bottomEnd else bottomStart
                val bottomLeft =
                    if (layoutDirection == LayoutDirection.Ltr) bottomStart else bottomEnd

                // Start at top-left corner (after arc)
                moveTo(0f + topLeft, 0f)

                // Top edge
                lineTo(width - topRight, 0f)
                arcTo(
                    Rect(width - 2 * topRight, 0f, width, 2 * topRight),
                    startAngleDegrees = -90f,
                    sweepAngleDegrees = 90f,
                    forceMoveTo = false
                )

                // Right edge
                lineTo(width, height - bottomRight)
                arcTo(
                    Rect(width - 2 * bottomRight, height - 2 * bottomRight, width, height),
                    startAngleDegrees = 0f,
                    sweepAngleDegrees = 90f,
                    forceMoveTo = false
                )

                // Bottom edge
                lineTo(bottomLeft, height)
                arcTo(
                    Rect(0f, height - 2 * bottomLeft, 2 * bottomLeft, height),
                    startAngleDegrees = 90f,
                    sweepAngleDegrees = 90f,
                    forceMoveTo = false
                )

                // Left edge
                lineTo(0f, topLeft)
                arcTo(
                    Rect(0f, 0f, 2 * topLeft, 2 * topLeft),
                    startAngleDegrees = 180f,
                    sweepAngleDegrees = 90f,
                    forceMoveTo = false
                )

                close()
            })
        }
    }

    override fun copy(
        topStart: CornerSize,
        topEnd: CornerSize,
        bottomEnd: CornerSize,
        bottomStart: CornerSize
    ): CutRoundedCornerShape = CutRoundedCornerShape(
        topStart = topStart,
        topEnd = topEnd,
        bottomEnd = bottomEnd,
        bottomStart = bottomStart
    )

    override fun toString(): String {
        return "RoundedCornerCustomShape(topStart = $topStart, topEnd = $topEnd, bottomEnd = $bottomEnd, bottomStart = $bottomStart)"
    }

    override fun equals(other: Any?): Boolean {
        return other is CutRoundedCornerShape &&
                topStart == other.topStart &&
                topEnd == other.topEnd &&
                bottomEnd == other.bottomEnd &&
                bottomStart == other.bottomStart
    }

    override fun hashCode(): Int {
        var result = topStart.hashCode()
        result = 31 * result + topEnd.hashCode()
        result = 31 * result + bottomEnd.hashCode()
        result = 31 * result + bottomStart.hashCode()
        return result
    }
}

fun CutRoundedCornerShape(
    topStart: Dp = 0.dp,
    topEnd: Dp = 0.dp,
    bottomEnd: Dp = 0.dp,
    bottomStart: Dp = 0.dp
) = CutRoundedCornerShape(
    topStart = CornerSize(topStart),
    topEnd = CornerSize(topEnd),
    bottomStart = CornerSize(bottomStart),
    bottomEnd = CornerSize(bottomEnd),
)
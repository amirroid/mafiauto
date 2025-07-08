package ir.amirroid.mafiauto.theme.theme

import androidx.compose.ui.unit.dp
import ir.amirroid.mafiauto.theme.core.Shapes
import ir.amirroid.mafiauto.theme.utils.CutRoundedCornerShape

internal val AppShapes = Shapes(
    small = CutRoundedCornerShape(
        topStart = 4.dp,
        bottomEnd = 4.dp,
        bottomStart = 1.dp,
        topEnd = 1.dp
    ),
    medium = CutRoundedCornerShape(
        topStart = 8.dp,
        bottomEnd = 8.dp,
        topEnd = 2.dp,
        bottomStart = 2.dp
    ),
    large = CutRoundedCornerShape(
        topStart = 16.dp,
        bottomEnd = 16.dp,
        topEnd = 4.dp,
        bottomStart = 4.dp
    ),
)
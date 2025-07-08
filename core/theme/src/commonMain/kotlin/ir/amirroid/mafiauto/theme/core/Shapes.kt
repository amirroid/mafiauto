package ir.amirroid.mafiauto.theme.core

import androidx.compose.runtime.Immutable
import androidx.compose.runtime.staticCompositionLocalOf
import ir.amirroid.mafiauto.theme.utils.CutRoundedCornerShape

@Immutable
data class Shapes(
    val small: CutRoundedCornerShape,
    val medium: CutRoundedCornerShape,
    val large: CutRoundedCornerShape
)

val LocalShapes = staticCompositionLocalOf<Shapes> { error("Shapes not provided") }
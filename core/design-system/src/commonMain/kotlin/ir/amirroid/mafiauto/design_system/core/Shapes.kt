package ir.amirroid.mafiauto.design_system.core

import androidx.compose.runtime.Immutable
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.graphics.Shape
import ir.amirroid.mafiauto.design_system.utils.CutRoundedCornerShape

@Immutable
data class Shapes(
    val small: CutRoundedCornerShape,
    val medium: CutRoundedCornerShape,
    val large: CutRoundedCornerShape
)

val LocalShapes = staticCompositionLocalOf<Shapes> { error("Shapes not provided") }
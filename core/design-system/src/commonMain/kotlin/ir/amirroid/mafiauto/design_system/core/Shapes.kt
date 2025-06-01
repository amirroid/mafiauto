package ir.amirroid.mafiauto.design_system.core

import androidx.compose.runtime.Immutable
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.graphics.Shape

@Immutable
data class Shapes(
    val small: Shape,
    val medium: Shape,
    val large: Shape
)

val LocalShapes = staticCompositionLocalOf<Shapes> { error("Shapes not provided") }
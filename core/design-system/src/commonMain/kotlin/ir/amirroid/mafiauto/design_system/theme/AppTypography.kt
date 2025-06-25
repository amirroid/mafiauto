package ir.amirroid.mafiauto.design_system.theme

import androidx.compose.runtime.Composable
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.sp
import ir.amirroid.mafiauto.design_system.core.Typography
import ir.amirroid.mafiauto.resources.Resources
import org.jetbrains.compose.resources.Font

val AppFontFamily: FontFamily
    @Composable
    get() = FontFamily(
        Font(Resources.fonts.sfBold, weight = FontWeight.Bold),
        Font(Resources.fonts.sfMedium, weight = FontWeight.Medium),
        Font(Resources.fonts.sfBlack, weight = FontWeight.Black),
        Font(Resources.fonts.sfNormal)
    )

val AppTypography = Typography(
    h1 = TextStyle(
        fontSize = 28.sp,
        fontWeight = FontWeight.Bold,
        textAlign = TextAlign.Center
    ),
    h2 = TextStyle(
        fontSize = 22.sp,
        fontWeight = FontWeight.SemiBold,
    ),
    h3 = TextStyle(
        fontSize = 20.sp,
        fontWeight = FontWeight.SemiBold,
    ),
    h4 = TextStyle(
        fontSize = 18.sp,
        fontWeight = FontWeight.Medium,
    ),
    body = TextStyle(
        fontSize = 16.sp,
        fontWeight = FontWeight.Normal,
    ),
    caption = TextStyle(
        fontSize = 14.sp,
        fontWeight = FontWeight.Light,
    ),
    button = TextStyle(
        fontSize = 16.sp,
        fontWeight = FontWeight.Medium,
    )
)
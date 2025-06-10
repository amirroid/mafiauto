package ir.amirroid.mafiauto.design_system.theme

import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.sp
import ir.amirroid.mafiauto.design_system.core.Typography

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
    body = TextStyle(
        fontSize = 16.sp,
        fontWeight = FontWeight.Normal,
    ),
    caption = TextStyle(
        fontSize = 12.sp,
        fontWeight = FontWeight.Light,
    ),
    button = TextStyle(
        fontSize = 16.sp,
        fontWeight = FontWeight.Medium,
    )
)
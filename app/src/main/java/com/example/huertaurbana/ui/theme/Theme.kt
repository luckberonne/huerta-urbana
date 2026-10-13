package com.example.huertaurbana.ui.theme

import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Shapes
import androidx.compose.material3.Typography
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

val LeafGreen = Color(0xFF3E7C4A)
val SoftGreen = Color(0xFFDCEBD7)
val Cream = Color(0xFFF6F3E8)
val Earth = Color(0xFF8A6A4B)
val DeepGreen = Color(0xFF1F3A26)
val WaterBlue = Color(0xFF4F8FA3)
val HarvestOrange = Color(0xFFD9822B)

private val Colors = lightColorScheme(
    primary = LeafGreen,
    onPrimary = Color.White,
    primaryContainer = SoftGreen,
    onPrimaryContainer = DeepGreen,
    secondary = Earth,
    onSecondary = Color.White,
    background = Cream,
    onBackground = DeepGreen,
    surface = Cream,
    onSurface = DeepGreen,
    surfaceVariant = SoftGreen,
    onSurfaceVariant = DeepGreen,
    error = Color(0xFFB3532F),
    errorContainer = Color(0xFFF6DCCF),
    onErrorContainer = Color(0xFF5A2410),
)

private val Type = Typography(
    headlineMedium = TextStyle(fontFamily = FontFamily.SansSerif, fontWeight = FontWeight.Bold, fontSize = 28.sp),
    titleMedium = TextStyle(fontFamily = FontFamily.SansSerif, fontWeight = FontWeight.SemiBold, fontSize = 18.sp),
    bodyLarge = TextStyle(fontFamily = FontFamily.SansSerif, fontSize = 16.sp),
    bodyMedium = TextStyle(fontFamily = FontFamily.SansSerif, fontSize = 14.sp),
    labelLarge = TextStyle(fontFamily = FontFamily.SansSerif, fontWeight = FontWeight.Medium, fontSize = 16.sp),
)

private val AppShapes = Shapes(
    small = RoundedCornerShape(12.dp),
    medium = RoundedCornerShape(20.dp),
    large = RoundedCornerShape(28.dp),
)

@Composable
fun HuertaUrbanaTheme(content: @Composable () -> Unit) {
    MaterialTheme(colorScheme = Colors, typography = Type, shapes = AppShapes, content = content)
}

package com.mikeapp.timer.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

// Enhanced base theme with better contrast and modern colors
private val LightColors = lightColors(
    primary = Color(0xFF6366F1),        // Modern indigo
    primaryVariant = Color(0xFF4338CA),  // Darker indigo
    secondary = Color(0xFF06D6A0),       // Modern teal
    secondaryVariant = Color(0xFF048A81), // Darker teal
    background = Color(0xFFFAFAFA),      // Softer white
    surface = Color(0xFFFFFFFF),
    error = Color(0xFFEF4444),           // Modern red
    onPrimary = Color.White,
    onSecondary = Color.White,
    onBackground = Color(0xFF1F2937),    // Dark gray instead of pure black
    onSurface = Color(0xFF1F2937),
    onError = Color.White
)

private val DarkColors = darkColors(
    primary = Color(0xFF818CF8),         // Lighter indigo for dark mode
    primaryVariant = Color(0xFF6366F1),  // Standard indigo
    secondary = Color(0xFF34D399),       // Lighter teal for dark mode
    secondaryVariant = Color(0xFF06D6A0), // Standard teal
    background = Color(0xFF0F172A),      // Deep navy
    surface = Color(0xFF1E293B),         // Slate gray
    error = Color(0xFFF87171),           // Lighter red for dark mode
    onPrimary = Color(0xFF1E1B4B),       // Dark indigo
    onSecondary = Color(0xFF064E3B),     // Dark teal
    onBackground = Color(0xFFF1F5F9),    // Light slate
    onSurface = Color(0xFFF1F5F9),
    onError = Color(0xFF7F1D1D)          // Dark red
)

@Composable
fun MyAppTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
    val colors = if (darkTheme) DarkColors else LightColors

    MaterialTheme(
        colors = colors,
        typography = EnhancedTypography,
        shapes = EnhancedShapes,
        content = content
    )
}

// Enhanced Typography
private val EnhancedTypography = Typography(
    defaultFontFamily = FontFamily.SansSerif,
    h1 = TextStyle(
        fontFamily = FontFamily.SansSerif,
        fontWeight = FontWeight.Light,
        fontSize = 96.sp,
        letterSpacing = (-1.5).sp
    ),
    h2 = TextStyle(
        fontFamily = FontFamily.SansSerif,
        fontWeight = FontWeight.Light,
        fontSize = 60.sp,
        letterSpacing = (-0.5).sp
    ),
    h3 = TextStyle(
        fontFamily = FontFamily.SansSerif,
        fontWeight = FontWeight.Normal,
        fontSize = 48.sp,
        letterSpacing = 0.sp
    ),
    h4 = TextStyle(
        fontFamily = FontFamily.SansSerif,
        fontWeight = FontWeight.Normal,
        fontSize = 34.sp,
        letterSpacing = 0.25.sp
    ),
    h5 = TextStyle(
        fontFamily = FontFamily.SansSerif,
        fontWeight = FontWeight.Normal,
        fontSize = 24.sp,
        letterSpacing = 0.sp
    ),
    h6 = TextStyle(
        fontFamily = FontFamily.SansSerif,
        fontWeight = FontWeight.Medium,
        fontSize = 20.sp,
        letterSpacing = 0.15.sp
    ),
    subtitle1 = TextStyle(
        fontFamily = FontFamily.SansSerif,
        fontWeight = FontWeight.Normal,
        fontSize = 16.sp,
        letterSpacing = 0.15.sp
    ),
    subtitle2 = TextStyle(
        fontFamily = FontFamily.SansSerif,
        fontWeight = FontWeight.Medium,
        fontSize = 14.sp,
        letterSpacing = 0.1.sp
    ),
    body1 = TextStyle(
        fontFamily = FontFamily.SansSerif,
        fontWeight = FontWeight.Normal,
        fontSize = 16.sp,
        letterSpacing = 0.5.sp
    ),
    body2 = TextStyle(
        fontFamily = FontFamily.SansSerif,
        fontWeight = FontWeight.Normal,
        fontSize = 14.sp,
        letterSpacing = 0.25.sp
    ),
    button = TextStyle(
        fontFamily = FontFamily.SansSerif,
        fontWeight = FontWeight.Medium,
        fontSize = 14.sp,
        letterSpacing = 1.25.sp
    ),
    caption = TextStyle(
        fontFamily = FontFamily.SansSerif,
        fontWeight = FontWeight.Normal,
        fontSize = 12.sp,
        letterSpacing = 0.4.sp
    ),
    overline = TextStyle(
        fontFamily = FontFamily.SansSerif,
        fontWeight = FontWeight.Normal,
        fontSize = 10.sp,
        letterSpacing = 1.5.sp
    )
)

// Enhanced Shapes with more rounded corners
private val EnhancedShapes = Shapes(
    small = androidx.compose.foundation.shape.RoundedCornerShape(8.dp),
    medium = androidx.compose.foundation.shape.RoundedCornerShape(12.dp),
    large = androidx.compose.foundation.shape.RoundedCornerShape(16.dp)
)
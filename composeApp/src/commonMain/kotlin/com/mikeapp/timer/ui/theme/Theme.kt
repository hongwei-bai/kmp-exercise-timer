package com.mikeapp.timer.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.unit.dp

// Enhanced base theme with better contrast and modern colors
private val LightColors = lightColors(
    primary = VividGreen,
    primaryVariant = VividBlue,
    onPrimary = VividBlack,
    secondary = VividOrange,
    secondaryVariant = VividPink,
    onSecondary = VividBlack,
    background = VividWhite,
    onBackground = VividBlack,
    surface = VividGrey,
    onSurface = VividBlack,
    error = VividDarkGrey,
    onError = VividBlack
)


private val DarkColors = darkColors(
    primary = VividGreen,
    primaryVariant = VividBlue,
    onPrimary = VividBlack,
    secondary = VividOrange,
    secondaryVariant = VividPink,
    onSecondary = VividBlack,
    background = VividBlack,
    onBackground = VividWhite,
    surface = VividBlack,
    onSurface = VividWhite,
    error = VividDarkGrey,
    onError = VividBlack
)

@Composable
fun MyAppTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
    val colors = if (darkTheme) DarkColors else LightColors
    val typography = Typography(
        defaultFontFamily = Exo2FontFamily()
    )

    MaterialTheme(
        colors = colors,
        typography = typography,
        shapes = EnhancedShapes,
        content = content
    )
}

// Enhanced Shapes with more rounded corners
private val EnhancedShapes = Shapes(
    small = androidx.compose.foundation.shape.RoundedCornerShape(8.dp),
    medium = androidx.compose.foundation.shape.RoundedCornerShape(12.dp),
    large = androidx.compose.foundation.shape.RoundedCornerShape(16.dp)
)
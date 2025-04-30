package com.example.kmpdemo.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

private val LightColors = lightColors(
    primary = Color(0xFF6200EE),
    secondary = Color(0xFF03DAC5),
    background = Color(0xFFFFFFFF),
    surface = Color(0xFFFFFFFF),
    onPrimary = Color.White,
    onSecondary = Color.Black,
    onBackground = Color.Black,
    onSurface = Color.Black,
)

private val DarkColors = darkColors(
    primary = Color(0xFFBB86FC),
    secondary = Color(0xFF03DAC5),
    background = Color(0xFF121212),
    surface = Color(0xFF121212),
    onPrimary = Color.Black,
    onSecondary = Color.Black,
    onBackground = Color.White,
    onSurface = Color.White,
)

@Composable
fun MyAppTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
    val colors = if (darkTheme) DarkColors else LightColors

    MaterialTheme(
        colors = colors,
        typography = Typography(),
        shapes = Shapes(),
        content = content
    )
}

@Composable
fun GreenTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
    val colors = if (darkTheme) {
        darkColors(
            primary = Color(0xFF4CAF50),  // Green Primary
            secondary = Color(0xFF8BC34A),  // Light Green Secondary
            surface = Color(0xFFFFFFFF),  // White Surface
            onPrimary = Color(0xFFFFFFFF),  // White on Primary
            onSurface = Color(0xFF000000),  // Black on Surface
            error = Color(0xFFF44336)
        )
    } else {
        lightColors(
            primary = Color(0xFF4CAF50),  // Green Primary
            secondary = Color(0xFF8BC34A),  // Light Green Secondary
            surface = Color(0xFFFFFFFF),  // White Surface
            onPrimary = Color(0xFFFFFFFF),  // White on Primary
            onSurface = Color(0xFF000000),  // Black on Surface
            error = Color(0xFFF44336)
        )
    }

    MaterialTheme(
        colors = colors,
        typography = Typography(),
        shapes = Shapes(),
        content = content
    )
}

@Composable
fun LightBlueTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
    val colors = if (darkTheme) {
        darkColors(
            primary = Color(0xFF03A9F4),  // Light Blue Primary
            secondary = Color(0xFF009688),  // Teal Secondary
            surface = Color(0xFFFFFFFF),  // White Surface
            onPrimary = Color(0xFFFFFFFF),  // White on Primary
            onSurface = Color(0xFF000000)  // Black on Surface
        )
    } else {
        lightColors(
            primary = Color(0xFF03A9F4),  // Light Blue Primary
            secondary = Color(0xFF009688),  // Teal Secondary
            surface = Color(0xFFFFFFFF),  // White Surface
            onPrimary = Color(0xFFFFFFFF),  // White on Primary
            onSurface = Color(0xFF000000)  // Black on Surface
        )
    }

    MaterialTheme(
        colors = colors,
        typography = Typography(),
        shapes = Shapes(),
        content = content
    )
}

@Composable
fun RedTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
    val colors = if (darkTheme) {
        darkColors(
            primary = Color(0xFFF44336),  // Red Primary
            secondary = Color(0xFFE91E63),  // Pink Secondary
            surface = Color(0xFFFFFFFF),  // White Surface
            onPrimary = Color(0xFFFFFFFF),  // White on Primary
            onSurface = Color(0xFF000000)  // Black on Surface
        )
    } else {
        lightColors(
            primary = Color(0xFFF44336),  // Red Primary
            secondary = Color(0xFFE91E63),  // Pink Secondary
            surface = Color(0xFFFFFFFF),  // White Surface
            onPrimary = Color(0xFFFFFFFF),  // White on Primary
            onSurface = Color(0xFF000000)  // Black on Surface
        )
    }

    MaterialTheme(
        colors = colors,
        typography = Typography(),
        shapes = Shapes(),
        content = content
    )
}

@Composable
fun AmberTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
    val colors = if (darkTheme) {
        darkColors(
            primary = Color(0xFFFF9800),  // Amber Primary
            secondary = Color(0xFFFF5722),  // Deep Orange Secondary
            surface = Color(0xFFFFFFFF),  // White Surface
            onPrimary = Color(0xFFFFFFFF),  // White on Primary
            onSurface = Color(0xFF000000)  // Black on Surface
        )
    } else {
        lightColors(
            primary = Color(0xFFFF9800),  // Amber Primary
            secondary = Color(0xFFFF5722),  // Deep Orange Secondary
            surface = Color(0xFFFFFFFF),  // White Surface
            onPrimary = Color(0xFFFFFFFF),  // White on Primary
            onSurface = Color(0xFF000000)  // Black on Surface
        )
    }

    MaterialTheme(
        colors = colors,
        typography = Typography(),
        shapes = Shapes(),
        content = content
    )
}

@Composable
fun IndigoTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
    val colors = if (darkTheme) {
        darkColors(
            primary = Color(0xFF3F51B5),  // Indigo Primary
            secondary = Color(0xFF2196F3),  // Blue Secondary
            surface = Color(0xFFFFFFFF),  // White Surface
            onPrimary = Color(0xFFFFFFFF),  // White on Primary
            onSurface = Color(0xFF000000)  // Black on Surface
        )
    } else {
        lightColors(
            primary = Color(0xFF3F51B5),  // Indigo Primary
            secondary = Color(0xFF2196F3),  // Blue Secondary
            surface = Color(0xFFFFFFFF),  // White Surface
            onPrimary = Color(0xFFFFFFFF),  // White on Primary
            onSurface = Color(0xFF000000)  // Black on Surface
        )
    }

    MaterialTheme(
        colors = colors,
        typography = Typography(),
        shapes = Shapes(),
        content = content
    )
}

@Composable
fun PinkTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
    val colors = if (darkTheme) {
        darkColors(
            primary = Color(0xFFE91E63),  // Pink Primary
            secondary = Color(0xFF9C27B0),  // Purple Secondary
            surface = Color(0xFFFFFFFF),  // White Surface
            onPrimary = Color(0xFFFFFFFF),  // White on Primary
            onSurface = Color(0xFF000000)  // Black on Surface
        )
    } else {
        lightColors(
            primary = Color(0xFFE91E63),  // Pink Primary
            secondary = Color(0xFF9C27B0),  // Purple Secondary
            surface = Color(0xFFFFFFFF),  // White Surface
            onPrimary = Color(0xFFFFFFFF),  // White on Primary
            onSurface = Color(0xFF000000)  // Black on Surface
        )
    }

    MaterialTheme(
        colors = colors,
        typography = Typography(),
        shapes = Shapes(),
        content = content
    )
}

@Composable
fun DeepPurpleTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
    val colors = if (darkTheme) {
        darkColors(
            primary = Color(0xFF673AB7),  // Deep Purple Primary
            secondary = Color(0xFF9C27B0),  // Purple Secondary
            surface = Color(0xFFFFFFFF),  // White Surface
            onPrimary = Color(0xFFFFFFFF),  // White on Primary
            onSurface = Color(0xFF000000)  // Black on Surface
        )
    } else {
        lightColors(
            primary = Color(0xFF673AB7),  // Deep Purple Primary
            secondary = Color(0xFF9C27B0),  // Purple Secondary
            surface = Color(0xFFFFFFFF),  // White Surface
            onPrimary = Color(0xFFFFFFFF),  // White on Primary
            onSurface = Color(0xFF000000)  // Black on Surface
        )
    }

    MaterialTheme(
        colors = colors,
        typography = Typography(),
        shapes = Shapes(),
        content = content
    )
}
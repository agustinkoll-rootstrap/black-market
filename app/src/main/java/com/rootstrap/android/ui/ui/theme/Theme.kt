package com.rootstrap.android.ui.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material.MaterialTheme
import androidx.compose.material.darkColors
import androidx.compose.material.lightColors
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

private val DarkColorPalette = darkColors(
    primary = Purple200,
    onPrimary = Color.White,
    secondaryVariant = BlueLink
)

private val LightColorPalette = lightColors(
    primary = RichBlack,
    background = BackgroundGray,
    onPrimary = Color.White,
    secondary = Color.LightGray,
    secondaryVariant = BlueLink
)

@Composable
fun AndroidBaseTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
    val colors = if (darkTheme) {
        DarkColorPalette
    } else {
        LightColorPalette
    }

    MaterialTheme(
        colors = colors,
        typography = Typography,
        shapes = Shapes,
        content = content
    )
}

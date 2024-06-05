package com.example.glamify.ui.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

@Composable
fun GlamifyTheme(
    colorPalette: AppColors,
    darkTheme: Boolean,
    content: @Composable () -> Unit
) {
    MaterialTheme(
        colorScheme = if (darkTheme) darkColorScheme(colorPalette) else lightColorScheme(colorPalette),
        typography = MaterialTheme.typography,
        content = content
    )
}

private fun darkColorScheme(palette: AppColors) = androidx.compose.material3.darkColorScheme(
    primary = palette.primary,
    onPrimary = palette.onPrimary,
    surface = palette.surface,
    onSurface = palette.onSurface,
    background = palette.background,
    onBackground = palette.onBackground,
    secondary = palette.secondary,
    onSecondary = palette.onSecondary,
    error = Color(0xFFCF6679),
    onError = Color.Black
)

private fun lightColorScheme(palette: AppColors) = androidx.compose.material3.lightColorScheme(
    primary = palette.primary,
    onPrimary = palette.onPrimary,
    surface = palette.surface,
    onSurface = palette.onSurface,
    background = palette.background,
    onBackground = palette.onBackground,
    secondary = palette.secondary,
    onSecondary = palette.onSecondary,
    error = Color(0xFFB00020),
    onError = Color.White
)

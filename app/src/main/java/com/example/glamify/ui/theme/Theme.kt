package com.example.glamify.ui.theme

//noinspection UsingMaterialAndMaterial3Libraries
import androidx.compose.material.MaterialTheme
//noinspection UsingMaterialAndMaterial3Libraries
import androidx.compose.material.MaterialTheme.shapes
//noinspection UsingMaterialAndMaterial3Libraries
import androidx.compose.material.MaterialTheme.typography
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

@Composable
fun GlamifyTheme(
    colorPalette: AppColors,
    darkTheme: Boolean,
    content: @Composable () -> Unit
) {
    MaterialTheme(
        colors = if (darkTheme) darkColors(colorPalette) else lightColors(colorPalette),
        typography = typography,
        shapes = shapes,
        content = content
    )
}

private fun darkColors(palette: AppColors) = androidx.compose.material.darkColors(
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

private fun lightColors(palette: AppColors) = androidx.compose.material.lightColors(
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

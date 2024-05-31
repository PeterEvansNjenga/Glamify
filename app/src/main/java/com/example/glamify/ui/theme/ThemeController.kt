package com.example.glamify.ui.theme

import androidx.compose.runtime.mutableStateOf

object ThemeController {
    val isDarkTheme = mutableStateOf(false)

    fun isDarkTheme(): Boolean {
        return isDarkTheme.value
    }

    fun toggleTheme() {
        isDarkTheme.value = !isDarkTheme.value
    }

    fun getCurrentColorPalette(): AppColors {
        return if (isDarkTheme()) {
            DarkColorPalette
        } else {
            LightColorPalette
        }
    }
}

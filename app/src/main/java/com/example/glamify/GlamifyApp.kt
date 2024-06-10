package com.example.glamify

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import com.example.glamify.navigation.AppNavHost
import com.example.glamify.ui.theme.GlamifyTheme
import com.example.glamify.ui.theme.ThemeController


@Composable
fun GlamifyApp(themeController: ThemeController) {
    val isDarkTheme by remember { mutableStateOf(themeController.isDarkTheme.value) }

    GlamifyTheme(
        colorPalette = themeController.getCurrentColorPalette(),
        darkTheme = isDarkTheme
    ) {
        AppNavHost(themeController = themeController)
    }
}

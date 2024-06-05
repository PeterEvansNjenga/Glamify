package com.example.glamify

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import com.example.glamify.navigation.AppNavHost
import com.example.glamify.ui.theme.GlamifyTheme
import com.example.glamify.ui.theme.ThemeController

class MainActivity : ComponentActivity() {
    private val themeController = ThemeController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            GlamifyApp(themeController = themeController)
        }
    }
}

@Composable
fun GlamifyApp(themeController: ThemeController) {
    var isDarkTheme by remember { mutableStateOf(themeController.isDarkTheme.value) }

    GlamifyTheme(
        colorPalette = themeController.getCurrentColorPalette(),
        darkTheme = isDarkTheme
    ) {
        AppNavHost(themeController = themeController)
    }
}

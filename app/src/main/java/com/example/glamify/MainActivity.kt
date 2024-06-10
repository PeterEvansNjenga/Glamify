package com.example.glamify

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
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

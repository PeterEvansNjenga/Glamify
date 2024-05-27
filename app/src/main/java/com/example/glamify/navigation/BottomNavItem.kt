package com.example.glamify.navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.List
import androidx.compose.material.icons.filled.AccountBox
import androidx.compose.material.icons.filled.AddCircle
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.ui.graphics.vector.ImageVector

sealed class BottomNavItem(val route: String, val icon: ImageVector, val title: String) {
    object Home : BottomNavItem(VIEW_USER_SHOE, Icons.Default.ShoppingCart, "Buy")
    object AddShoes : BottomNavItem(ADD_SHOES_URL, Icons.Default.AddCircle, "Sell")
    object ViewShoes : BottomNavItem(VIEW_SHOES_URL, Icons.AutoMirrored.Filled.List, "Uploads")
    object Account : BottomNavItem(ACCOUNT_URL, Icons.Default.AccountBox, "Account")
    object About : BottomNavItem(ABOUT_URL, Icons.Default.Info, "Help")
}

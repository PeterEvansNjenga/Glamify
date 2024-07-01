package com.example.glamify.navigation

//noinspection UsingMaterialAndMaterial3Libraries
import androidx.compose.material.BottomNavigation
//noinspection UsingMaterialAndMaterial3Libraries
import androidx.compose.material.BottomNavigationItem
//noinspection UsingMaterialAndMaterial3Libraries
import androidx.compose.material.Icon
//noinspection UsingMaterialAndMaterial3Libraries
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.example.glamify.navigation.BottomNavItem.About
import com.example.glamify.navigation.BottomNavItem.Account
import com.example.glamify.navigation.BottomNavItem.AddShoes
import com.example.glamify.navigation.BottomNavItem.Home
import com.example.glamify.navigation.BottomNavItem.ViewShoes

@Composable
fun BottomNavigationBar(navController: NavHostController) {
    val items = listOf(
        Home,
        AddShoes,
        ViewShoes,
        Account,
        About
    )

    val navBackStackEntry = navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry.value?.destination?.route

    if (currentRoute !in listOf(LOGIN_URL, SIGNUP_URL, SPLASH_URL)) {
        BottomNavigation(
            backgroundColor = Color.Black,
            contentColor = Color.White
        ) {
            items.forEach { item ->
                BottomNavigationItem(
                    icon = { Icon(imageVector = item.icon, contentDescription = item.title) },
                    label = { Text(text = item.title) },
                    selected = currentRoute == item.route,
                    onClick = {
                        navController.navigate(item.route) {
                            popUpTo(navController.graph.startDestinationId)
                            launchSingleTop = true
                        }
                    },
                    selectedContentColor = Color.Cyan,
                    unselectedContentColor = Color.White
                )
            }
        }
    }
}

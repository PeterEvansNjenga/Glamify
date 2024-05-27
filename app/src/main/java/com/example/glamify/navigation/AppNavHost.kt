package com.example.glamify.navigation

import androidx.compose.foundation.layout.padding
import androidx.compose.material.Scaffold
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.glamify.ui.theme.screens.about.AboutScreen
import com.example.glamify.ui.theme.screens.account.AccountScreen
import com.example.glamify.ui.theme.screens.login.LoginScreen
import com.example.glamify.ui.theme.screens.products.AddShoesScreen
import com.example.glamify.ui.theme.screens.products.HomeScreen
import com.example.glamify.ui.theme.screens.products.ViewShoesScreen
import com.example.glamify.ui.theme.screens.signup.SignupScreen
import com.example.glamify.models.AuthViewModel

@Composable
fun AppNavHost(
    navController: NavHostController = rememberNavController(),
    startDestination: String = rememberAppStartDestination(navController)
) {
    Scaffold(
        bottomBar = {
            if (startDestination != LOGIN_URL && startDestination != SIGNUP_URL) {
                BottomNavigationBar(navController = navController)
            }
        }
    ) { innerPadding ->
        NavHost(
            navController = navController,
            startDestination = startDestination,
            modifier = Modifier.padding(innerPadding)
        ) {
            composable(LOGIN_URL) {
                LoginScreen(navController = navController)
            }
            composable(SIGNUP_URL) {
                SignupScreen(navController = navController)
            }
            composable(ADD_SHOES_URL) {
                AddShoesScreen(navController = navController)
            }
            composable(VIEW_SHOES_URL) {
                ViewShoesScreen(navController = navController)
            }
            composable(ACCOUNT_URL) {
                AccountScreen(navController = navController)
            }
            composable(ABOUT_URL) {
                AboutScreen()
            }
            composable(VIEW_USER_SHOE) {
                HomeScreen(navController = navController)
            }
        }
    }
}

@Composable
fun rememberAppStartDestination(navController: NavHostController): String {
    val context = LocalContext.current
    val authViewModel = remember(context) { AuthViewModel(navController, context) }

    return if (authViewModel.isLoggedIn() && !authViewModel.isFirstTimeUser()) {
        VIEW_USER_SHOE
    } else {
        LOGIN_URL
    }
}

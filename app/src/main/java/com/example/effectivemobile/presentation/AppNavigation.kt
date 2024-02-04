package com.example.effectivemobile.presentation

import androidx.annotation.DrawableRes
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.effectivemobile.R
import com.example.effectivemobile.presentation.main_screen.MainScreen
import com.example.effectivemobile.presentation.registration_screen.RegistrationScreen

@Composable
fun AppNavigation(mainScreen: Screen) {
    val navController = rememberNavController()
    Scaffold { innerPadding ->
        NavHost(navController, mainScreen.route, Modifier.padding(innerPadding)) {
            composable(Screen.Registration.route) {
                RegistrationScreen(navController)
            }
            composable(Screen.Main.route) {
                MainScreen()
            }
        }
    }
}

sealed class Screen(val route: String, val name: String) {
    data object Registration : Screen("registration", "Вход")
    data object Main : Screen("main", "Main")
}

sealed class BottomNavItem(val route: String, @DrawableRes val iconRes: Int, val label: String) {
    data object Home : BottomNavItem("home", R.drawable.ic_home, "Главная")
    data object Catalog : BottomNavItem("catalog", R.drawable.ic_catalog, "Каталог")
    data object Cart : BottomNavItem("cart", R.drawable.ic_cart, "Корзина")
    data object Discount : BottomNavItem("discount", R.drawable.ic_discount, "Акции")
    data object Profile : BottomNavItem("profile", R.drawable.ic_profile, "Профиль")
    data object Details : BottomNavItem("details", R.drawable.ic_profile, "Details")
    data object Favorite : BottomNavItem("favorite", R.drawable.ic_profile, "Favorite")
}
package com.example.effectivemobile.presentation.main_screen

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.effectivemobile.domain.model.ProductDomain
import com.example.effectivemobile.presentation.BottomNavItem
import com.example.effectivemobile.presentation.details_screen.DetailsScreen
import com.example.effectivemobile.presentation.main_screen.cart_screen.CartScreen
import com.example.effectivemobile.presentation.main_screen.catalog_screen.CatalogScreen
import com.example.effectivemobile.presentation.main_screen.discount_screen.DiscountScreen
import com.example.effectivemobile.presentation.main_screen.favorite_screen.FavoriteScreen
import com.example.effectivemobile.presentation.main_screen.home_screen.HomeScreen
import com.example.effectivemobile.presentation.main_screen.profile_screen.ProfileScreen

@Composable
fun MainScreen() {
    val viewModel: MainScreenViewModel = hiltViewModel()
    val bottomNavController = rememberNavController()

    Scaffold(
        bottomBar = {
            AppBottomNavigation(
                bottomNavController, listOf(
                    BottomNavItem.Home,
                    BottomNavItem.Catalog,
                    BottomNavItem.Cart,
                    BottomNavItem.Discount,
                    BottomNavItem.Profile,
                )
            )
        },
    ) { paddingValues ->
        NavigationHost(
            navController = bottomNavController,
            modifier = Modifier.padding(paddingValues)
        )
    }
}

@Composable
private fun AppBottomNavigation(
    navController: NavHostController,
    items: List<BottomNavItem>
) {
    var selectedItem by remember {
        mutableStateOf(
            BottomNavItem.Home.route
        )
    }

    navController.addOnDestinationChangedListener { _, destination, _ ->
        destination.route?.let {
            selectedItem = it
        }
    }

    NavigationBar(
        containerColor = Color.White
    ) {
        items.forEach { screen ->
            NavigationBarItem(
                icon = {
                    Icon(
                        painter = painterResource(screen.iconRes),
                        contentDescription = null
                    )
                },
                label = { Text(screen.label) },
                selected = selectedItem == screen.route,
                colors = NavigationBarItemDefaults.colors(
                    selectedIconColor = MaterialTheme.colorScheme.primary,
                    selectedTextColor = MaterialTheme.colorScheme.primary,
                    indicatorColor = MaterialTheme.colorScheme.background,
                ),
                onClick = {
                    if (selectedItem != screen.route) {
                        selectedItem = screen.route
                        navController.navigate(
                            route = screen.route,
                            builder = {
                                popUpTo(navController.graph.findStartDestination().id) {
                                    saveState = true
                                }
                                launchSingleTop = true
                                restoreState = true
                            }
                        )
                    }
                }
            )
        }
    }
}

@Composable
fun NavigationHost(
    navController: NavController,
    modifier: Modifier = Modifier
) {
    NavHost(
        navController = navController as NavHostController,
        startDestination = BottomNavItem.Catalog.route,
        modifier = modifier
    ) {
        composable(BottomNavItem.Home.route) { HomeScreen(navController) }
        composable(BottomNavItem.Catalog.route) { CatalogScreen(navController) }
        composable(BottomNavItem.Cart.route) { CartScreen() }
        composable(BottomNavItem.Discount.route) { DiscountScreen() }
        composable(BottomNavItem.Profile.route) { ProfileScreen(navController) }
        composable(BottomNavItem.Favorite.route) { FavoriteScreen(navController) }
        composable(BottomNavItem.Details.route) {
            val productData = navController.previousBackStackEntry
                ?.savedStateHandle?.get<ProductDomain>("product")
            DetailsScreen(navController, productData)
        }
    }
}
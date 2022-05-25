package com.example.ddinerapp.featureMain.presentation.home

import androidx.compose.foundation.layout.padding
import androidx.compose.material.BottomNavigation
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material.Icon
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.ddinerapp.featureMain.presentation.cart.CartScreen
import com.example.ddinerapp.featureMain.presentation.menu.MenuScreen
import com.example.ddinerapp.featureMain.presentation.orders.OrdersScreen
import com.example.ddinerapp.featureMain.presentation.settings.SettingsScreen
import com.example.ddinerapp.featureMain.presentation.utils.BottomNavItem
import com.example.ddinerapp.featureMain.presentation.utils.Screen

@Composable
fun HomeScreen() {
    val screens = listOf(
        BottomNavItem.MenuItem,
        BottomNavItem.OrdersItem,
        BottomNavItem.CartItem,
        BottomNavItem.SettingsItem
    )
    val navController = rememberNavController()

    Scaffold(
        bottomBar = {
            BottomNavigation {
                val navBackStackEntry by navController.currentBackStackEntryAsState()
                val currentDestination = navBackStackEntry?.destination
                screens.forEach { screen ->
                    BottomNavigationItem(
                        icon = {
                            Icon(
                                screen.icon,
                                contentDescription = screen.contentDescription
                            )
                        },
                        selected = currentDestination?.hierarchy?.any { it.route == screen.route } == true,
                        onClick = {
                            navController.navigate(screen.route) {
                                popUpTo(navController.graph.findStartDestination().id) {
                                    saveState = true
                                }
                                launchSingleTop = true
                                restoreState = true
                            }
                        })
                }
            }
        }
    ) {
        NavHost(
            navController = navController,
            startDestination = Screen.MenuScreen.route,
            modifier = Modifier.padding(it)
        ) {
            composable(Screen.MenuScreen.route) {
                MenuScreen(navController = navController)
            }
            composable(route = Screen.OrdersScreen.route) {
                OrdersScreen(navController = navController)
            }
            composable(route = Screen.CartScreen.route) {
                CartScreen(navController = navController)
            }
            composable(route = Screen.SettingsScreen.route) {
                SettingsScreen(navController = navController)
            }
        }
    }
}
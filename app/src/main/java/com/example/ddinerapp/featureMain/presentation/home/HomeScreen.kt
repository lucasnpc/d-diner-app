package com.example.ddinerapp.featureMain.presentation.home

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.ddinerapp.featureMain.presentation.cart.CartScreen
import com.example.ddinerapp.featureMain.presentation.orderingItems.OrderingItemsScreen
import com.example.ddinerapp.featureMain.presentation.orderingMenu.OrderingMenuScreen
import com.example.ddinerapp.featureMain.presentation.orders.OrdersScreen
import com.example.ddinerapp.featureMain.presentation.utils.BottomNavItem
import com.example.ddinerapp.featureMain.presentation.utils.Screen

@Composable
fun HomeScreen() {
    val screens = listOf(
        BottomNavItem.MenuItem,
        BottomNavItem.CartItem,
        BottomNavItem.OrdersItem,
    )
    val navController = rememberNavController()

    Scaffold(
        bottomBar = {
            BottomNavBar(navController, screens)
        }
    ) {
        NavHost(
            navController = navController,
            startDestination = Screen.OrderingMenuScreen.route,
            modifier = Modifier
                .padding(it)
                .fillMaxSize()
                .background(
                    Brush.verticalGradient(
                        colors = listOf(
                            Color(0xFFFFFFFF),
                            Color(0xFFC4C4C4)
                        )
                    )
                )
        ) {
            composable(route = Screen.OrderingMenuScreen.route) {
                OrderingMenuScreen(navController = navController)
            }
            composable(
                route = Screen.OrderingItemsScreen.route + "/{itemCategory}",
                arguments = listOf(navArgument("itemCategory") {
                    type = NavType.StringType
                    defaultValue = ""
                })
            ) { backStackEntry ->
                OrderingItemsScreen(
                    navController = navController,
                    backStackEntry.arguments?.getString("itemCategory"),
                )
            }
            composable(route = Screen.OrdersScreen.route) {
                OrdersScreen()
            }
            composable(route = Screen.CartScreen.route) {
                CartScreen(navController = navController)
            }
        }
    }
}

@Composable
private fun BottomNavBar(
    navController: NavHostController,
    screens: List<BottomNavItem>
) {
    BottomNavigation(backgroundColor = MaterialTheme.colors.surface) {
        val navBackStackEntry by navController.currentBackStackEntryAsState()
        val currentDestination = navBackStackEntry?.destination
        screens.forEach { screen ->
            BottomNavigationItem(
                icon = {
                    Icon(
                        screen.icon,
                        contentDescription = screen.contentDescription,
                        tint = MaterialTheme.colors.onSurface
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
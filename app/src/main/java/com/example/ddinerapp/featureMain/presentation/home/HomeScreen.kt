package com.example.ddinerapp.featureMain.presentation.home

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
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
import com.example.ddinerapp.featureMain.presentation.settings.SettingsScreen
import com.example.ddinerapp.featureMain.presentation.utils.BottomNavItem
import com.example.ddinerapp.featureMain.presentation.utils.Screen

@Composable
fun HomeScreen(
    viewModel: HomeViewModel = hiltViewModel(),
    desk: String?
) {
    val screens = listOf(
        BottomNavItem.MenuItem,
        BottomNavItem.OrdersItem,
        BottomNavItem.CartItem,
        BottomNavItem.SettingsItem
    )
    val navController = rememberNavController()
    // viewModel.getUserRole().collectAsState(initial = "")
    Scaffold(
        topBar = {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically,
            ) {
                OutlinedButton(
                    onClick = {/*TODO*/ },
                    border = BorderStroke(1.dp, MaterialTheme.colors.secondary),
                    shape = RoundedCornerShape(16.dp)
                ) {
                    Icon(
                        imageVector = Icons.Filled.ArrowBack,
                        contentDescription = "User Image",
                        tint = MaterialTheme.colors.primaryVariant,
                        modifier = Modifier
                            .width(32.dp)
                            .height(32.dp)
                    )
                }
                Text(text = desk.toString(), fontSize = 20.sp, color = Color.Black)
                OutlinedButton(
                    onClick = { /*TODO*/ },
                    border = BorderStroke(1.dp, MaterialTheme.colors.secondary),
                    shape = RoundedCornerShape(16.dp)
                ) {
                    Icon(
                        imageVector = Icons.Filled.Notifications,
                        contentDescription = "Notification",
                        tint = MaterialTheme.colors.primaryVariant,
                        modifier = Modifier
                            .width(32.dp)
                            .height(32.dp)
                    )
                }
            }
        },
        bottomBar = {
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
                    backStackEntry.arguments?.getString("itemCategory")
                )
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
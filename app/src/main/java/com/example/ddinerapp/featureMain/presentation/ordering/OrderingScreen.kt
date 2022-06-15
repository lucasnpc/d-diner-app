package com.example.ddinerapp.featureMain.presentation.ordering

import androidx.compose.foundation.layout.padding
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.ddinerapp.featureMain.presentation.orderingDelivery.OrderingDeliveryScreen
import com.example.ddinerapp.featureMain.presentation.orderingDesks.OrderingDesksScreen
import com.example.ddinerapp.featureMain.presentation.orderingItems.OrderingItemsScreen
import com.example.ddinerapp.featureMain.presentation.orderingMenu.OrderingMenuScreen
import com.example.ddinerapp.featureMain.presentation.orderingType.OrderingTypeScreen
import com.example.ddinerapp.featureMain.presentation.utils.OrderingScreens

@Composable
fun OrderingScreen() {
    val navController = rememberNavController()
    Scaffold {
        NavHost(
            navController = navController,
            startDestination = OrderingScreens.OrderingTypeScreen.route,
            modifier = Modifier.padding(it)
        ) {
            composable(route = OrderingScreens.OrderingTypeScreen.route) {
                OrderingTypeScreen(navController = navController)
            }
            composable(route = OrderingScreens.OrderingDesksScreen.route) {
                OrderingDesksScreen(navController = navController)
            }
            composable(route = OrderingScreens.OrderingDeliveryScreen.route) {
                OrderingDeliveryScreen(navController = navController)
            }
            composable(route = OrderingScreens.OrderingMenuScreen.route) {
                OrderingMenuScreen(navController = navController)
            }
            composable(
                route = OrderingScreens.OrderingItemsScreen.route + "/{itemCategory}",
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
        }
    }
}
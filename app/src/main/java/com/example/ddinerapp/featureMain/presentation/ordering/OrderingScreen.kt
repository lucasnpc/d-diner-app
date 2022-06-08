package com.example.ddinerapp.featureMain.presentation.ordering

import androidx.compose.foundation.layout.padding
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.ddinerapp.featureMain.presentation.orderingDelivery.OrderingDeliveryScreen
import com.example.ddinerapp.featureMain.presentation.orderingDesks.OrderingDesksScreen
import com.example.ddinerapp.featureMain.presentation.orderingItems.OrderingItemsScreen
import com.example.ddinerapp.featureMain.presentation.orderingMenu.OrderingMenuScreen
import com.example.ddinerapp.featureMain.presentation.orderingType.OrderingType
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
            composable(OrderingScreens.OrderingTypeScreen.route) {
                OrderingType(navController = navController)
            }
            composable(OrderingScreens.OrderingDesksScreen.route) {
                OrderingDesksScreen(navController = navController)
            }
            composable(OrderingScreens.OrderingDeliveryScreen.route) {
                OrderingDeliveryScreen(navController = navController)
            }
            composable(OrderingScreens.OrderingMenuScreen.route) {
                OrderingMenuScreen(navController = navController)
            }
            composable(OrderingScreens.OrderingItemsScreen.route) {
                OrderingItemsScreen(navController = navController)
            }
        }
    }
}
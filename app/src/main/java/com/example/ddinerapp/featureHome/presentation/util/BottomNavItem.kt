package com.example.ddinerapp.featureHome.presentation.util

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.MenuBook
import androidx.compose.material.icons.filled.RestaurantMenu
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.ui.graphics.vector.ImageVector

sealed class BottomNavItem(
    val icon: ImageVector,
    val contentDescription: String,
    val route: String
) {
    object MenuItem :
        BottomNavItem(
            icon = Icons.Filled.RestaurantMenu,
            contentDescription = "Menu Icon",
            route = HomeScreen.OrderingMenuScreen.route
        )

    object OrdersItem :
        BottomNavItem(
            icon = Icons.Filled.MenuBook,
            contentDescription = "Orders Icon",
            route = HomeScreen.OrdersScreen.route
        )

    object CartItem :
        BottomNavItem(
            icon = Icons.Filled.ShoppingCart,
            contentDescription = "Cart Icon",
            route = HomeScreen.CartScreen.route
        )
}

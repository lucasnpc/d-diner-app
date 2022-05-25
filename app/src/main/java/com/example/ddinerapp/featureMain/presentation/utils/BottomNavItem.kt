package com.example.ddinerapp.featureMain.presentation.utils

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.MenuBook
import androidx.compose.material.icons.filled.RestaurantMenu
import androidx.compose.material.icons.filled.Settings
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
            route = Screen.MenuScreen.route
        )

    object OrdersItem :
        BottomNavItem(
            icon = Icons.Filled.MenuBook,
            contentDescription = "Orders Icon",
            route = Screen.OrdersScreen.route
        )

    object CartItem :
        BottomNavItem(
            icon = Icons.Filled.ShoppingCart,
            contentDescription = "Cart Icon",
            route = Screen.CartScreen.route
        )

    object SettingsItem :
        BottomNavItem(
            icon = Icons.Filled.Settings,
            contentDescription = "Settings Icon",
            route = Screen.SettingsScreen.route
        )
}

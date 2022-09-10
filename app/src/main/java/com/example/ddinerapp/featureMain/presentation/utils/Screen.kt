package com.example.ddinerapp.featureMain.presentation.utils

sealed class Screen(val route: String) {
    object OrderingTypeScreen : Screen("ordering_type_screen")
    object OrderingDesksScreen : Screen("ordering_desks_screen")
    object OrderingDeliveryScreen : Screen("ordering_delivery_screen")
    object HomeScreen : Screen("home_screen")
    object OrderingMenuScreen : Screen("ordering_menu_screen")
    object OrderingItemsScreen : Screen("ordering_items_screen")
    object OrdersScreen : Screen("orders_screen")
    object CartScreen : Screen("cart_screen")
    object SettingsScreen : Screen("settings_screen")
}
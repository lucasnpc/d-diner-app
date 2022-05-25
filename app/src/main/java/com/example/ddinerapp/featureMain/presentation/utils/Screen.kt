package com.example.ddinerapp.featureMain.presentation.utils

sealed class Screen(val route: String) {
    object LoginScreen : Screen("login_screen")
    object HomeScreen : Screen("home_screen")
    object MenuScreen : Screen("menu_screen")
    object OrdersScreen : Screen("orders_screen")
    object CartScreen : Screen("cart_screen")
    object SettingsScreen : Screen("settings_screen")
}

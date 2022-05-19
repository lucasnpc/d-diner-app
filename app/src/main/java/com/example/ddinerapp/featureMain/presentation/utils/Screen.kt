package com.example.ddinerapp.featureMain.presentation.utils

sealed class Screen(val route: String) {
    object LoginScreen : Screen("login_screen")
    object DesksScreen : Screen("desks_screen")
    object HomeScreen : Screen("home_screen")
    object OrdersScreen : Screen("orders_screen")
    object CartScreen : Screen("cart_screen")
    object NotificationScreen : Screen("notification_screen")
}

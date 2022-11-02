package com.example.ddinerapp.featureHome.presentation.util

sealed class HomeScreen(val route: String) {
    object OrderingMenuScreen : HomeScreen("ordering_menu_screen")
    object OrderingItemsScreen : HomeScreen("ordering_items_screen")
    object OrdersScreen : HomeScreen("orders_screen")
    object CartScreen : HomeScreen("cart_screen")
    object MakeYourPizzaScreen : HomeScreen("make_your_pizza_screen")
    object PaymentVoucherScreen: HomeScreen("payment_voucher_screen")
}

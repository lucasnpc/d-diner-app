package com.example.ddinerapp.featureMain.presentation.utils

sealed class OrderingScreens(val route: String) {
    object OrderingTypeScreen : OrderingScreens("ordering_type_screen")
    object OrderingDesksScreen : OrderingScreens("ordering_desks_screen")
    object OrderingDeliveryScreen : OrderingScreens("ordering_delivery_screen")
    object OrderingMenuScreen : OrderingScreens("ordering_menu_screen")
    object OrderingItemsScreen : OrderingScreens("ordering_items_screen")
}

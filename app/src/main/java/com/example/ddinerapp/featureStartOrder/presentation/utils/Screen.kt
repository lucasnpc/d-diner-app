package com.example.ddinerapp.featureStartOrder.presentation.utils

sealed class Screen(val route: String) {
    object OrderingTypeScreen : Screen("ordering_type_screen")
    object OrderingDesksScreen : Screen("ordering_desks_screen")
    object OrderingDeliveryScreen : Screen("ordering_delivery_screen")
}
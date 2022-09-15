package com.example.ddinerapp.featureHome.domain.useCases

data class HomeUseCases(
    val getOrderedItemsUseCase: GetOrderedItemsUseCase,
    val placeOrdersUseCase: PlaceOrdersUseCase,
    val getDeskOrders: GetDeskOrders,
    val getMenuItemsUseCase: GetMenuItemsUseCase,
)
package com.example.ddinerapp.featureHome.domain.useCases

data class HomeUseCases(
    val getOrderedItemsUseCase: GetOrderedItemsUseCase,
    val placeOrdersUseCase: PlaceOrdersUseCase,
    val getMenuItemsUseCase: GetMenuItemsUseCase,
    val getCurrentDeskOrder: GetCurrentDeskOrder,
    val registerGainUseCase: RegisterGainUseCase,
    val getItemProducts: GetItemProducts
)
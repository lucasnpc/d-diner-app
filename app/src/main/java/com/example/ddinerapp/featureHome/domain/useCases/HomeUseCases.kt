package com.example.ddinerapp.featureHome.domain.useCases

data class HomeUseCases(
    val getOrderedItemsUseCase: GetOrderedItemsUseCase,
    val placeOrdersUseCase: PlaceOrdersUseCase,
    val getConcludedDeskOrders: GetConcludedDeskOrders,
    val getMenuItemsUseCase: GetMenuItemsUseCase,
    val concludeOrderUseCase: ConcludeOrderUseCase,
    val getCurrentDeskOrder: GetCurrentDeskOrder,
    val registerGainUseCase: RegisterGainUseCase,
    val getItemProducts: GetItemProducts
)
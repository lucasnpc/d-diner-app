package com.example.ddinerapp.featureMain.domain.useCases

data class MainUseCases(
    val authenticateUserUseCase: AuthenticateUserUseCase,
    val getDesksUseCase: GetDesksUseCase,
    val getMenuItemsUseCase: GetMenuItemsUseCase,
    val setOccupiedDeskUseCase: SetOccupiedDeskUseCase,
    val getDeskOrders: GetDeskOrders,
    val addOrderUseCase: AddOrderUseCase
)
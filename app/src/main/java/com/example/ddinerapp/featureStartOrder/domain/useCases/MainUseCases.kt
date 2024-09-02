package com.example.ddinerapp.featureStartOrder.domain.useCases

data class MainUseCases(
    val getDesksUseCase: GetDesksUseCase,
    val setOccupiedDeskUseCase: SetOccupiedDeskUseCase,
    val addOrderUseCase: AddOrderUseCase,
    val disoccupyDeskUseCase: DisoccupyDeskUseCase
)
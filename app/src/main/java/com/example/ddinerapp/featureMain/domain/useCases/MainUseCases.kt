package com.example.ddinerapp.featureMain.domain.useCases

data class MainUseCases(
    val getDesksUseCase: GetDesksUseCase,
    val setOccupiedDeskUseCase: SetOccupiedDeskUseCase,
    val addOrderUseCase: AddOrderUseCase,
    val disoccupyDeskUseCase: DisoccupyDeskUseCase
)
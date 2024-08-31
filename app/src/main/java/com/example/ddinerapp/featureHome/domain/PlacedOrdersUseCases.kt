package com.example.ddinerapp.featureHome.domain

import com.example.ddinerapp.featureHome.domain.placedOrdersUseCases.DeskCompletedOrdersUseCase
import com.example.ddinerapp.featureHome.domain.placedOrdersUseCases.CompleteOrderUseCase

data class PlacedOrdersUseCases(
    val deskCompletedOrdersUseCase: DeskCompletedOrdersUseCase,
    val completeOrderUseCase: CompleteOrderUseCase
)

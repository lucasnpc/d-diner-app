package com.example.ddinerapp.featureHome.domain.placedOrdersUseCases

interface CompleteOrderUseCase {

    fun completeOrder(
        cnpj: String,
        deskId: String,
        orderId: String,
        time: Long
    )

}
package com.example.ddinerapp.featureHome.data.repositories

import com.example.ddinerapp.featureHome.domain.placedOrdersUseCases.CompleteOrderUseCase

class FakeCompleteOrderRepository: CompleteOrderUseCase {
    override fun completeOrder(cnpj: String, deskId: String, orderId: String, time: Long) {
        TODO("Not yet implemented")
    }
}
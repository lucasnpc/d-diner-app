package com.example.ddinerapp.featureHome.domain.placedOrdersUseCases

import com.google.android.gms.tasks.Task

interface CompleteOrderUseCase {

    fun completeOrder(
        cnpj: String,
        deskId: String,
        orderId: String,
        time: Long
    ): Task<Void>

}
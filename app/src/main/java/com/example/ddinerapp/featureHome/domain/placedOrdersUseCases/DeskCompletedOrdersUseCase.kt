package com.example.ddinerapp.featureHome.domain.placedOrdersUseCases

import com.example.ddinerapp.common.data.request.ApiResult
import com.example.ddinerapp.featureHome.domain.model.Order
import kotlinx.coroutines.flow.Flow

interface DeskCompletedOrdersUseCase {

    fun getCompletedOrders(cnpj: String, deskId: String): Flow<ApiResult<Order>>

}
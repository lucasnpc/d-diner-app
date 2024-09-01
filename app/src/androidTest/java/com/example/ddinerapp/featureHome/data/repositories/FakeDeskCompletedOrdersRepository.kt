package com.example.ddinerapp.featureHome.data.repositories

import com.example.ddinerapp.common.data.request.ApiResult
import com.example.ddinerapp.featureHome.domain.model.Order
import com.example.ddinerapp.featureHome.domain.placedOrdersUseCases.DeskCompletedOrdersUseCase
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class FakeDeskCompletedOrdersRepository : DeskCompletedOrdersUseCase {
    override fun getCompletedOrders(cnpj: String, deskId: String): Flow<ApiResult<Order>> {
        return flow { emit(ApiResult.Success(Order(concluded = true))) }
    }
}
package com.example.ddinerapp.featureHome.presentation.placedOrders

import com.example.ddinerapp.common.util.DataStoreManager
import com.example.ddinerapp.commons.MainCoroutineRule
import com.example.ddinerapp.featureHome.domain.PlacedOrdersUseCases
import com.example.ddinerapp.featureHome.domain.placedOrdersUseCases.CompleteOrderUseCase
import com.example.ddinerapp.featureHome.domain.placedOrdersUseCases.DeskCompletedOrdersUseCase
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flow
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class PlacedOrdersViewModelTest {

    @get:Rule
    val mainCoroutineRule = MainCoroutineRule()

    private lateinit var placedOrdersViewModel: PlacedOrdersViewModel
    private val store: DataStoreManager = mockk(relaxed = true)
    private lateinit var placedOrdersUseCases: PlacedOrdersUseCases
    private val deskCompletedOrdersUseCase: DeskCompletedOrdersUseCase = mockk(relaxed = true)
    private val completeOrderUseCase: CompleteOrderUseCase = mockk(relaxed = true)

    @Before
    fun setUp() {
        coEvery { store.businessCnpj } returns flow {
            emit("12345678901234")
        }
        coEvery { store.deskId } returns flow {
            emit("12222222222222")
        }
        placedOrdersUseCases =
            PlacedOrdersUseCases(
                deskCompletedOrdersUseCase = deskCompletedOrdersUseCase,
                completeOrderUseCase = completeOrderUseCase
            )
        placedOrdersViewModel =
            PlacedOrdersViewModel(
                storeManager = store,
                placedOrdersUseCases = placedOrdersUseCases
            )
    }

    @Test
    fun getOrders() {

    }

    @Test
    fun concludeOrder() {
    }

    @Test
    fun writeDoc() {
    }
}
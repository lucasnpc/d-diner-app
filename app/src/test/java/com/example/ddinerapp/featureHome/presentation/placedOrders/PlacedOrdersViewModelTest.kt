package com.example.ddinerapp.featureHome.presentation.placedOrders

import android.content.Context
import android.graphics.pdf.PdfDocument
import android.os.Environment
import com.example.ddinerapp.common.data.request.ApiResult
import com.example.ddinerapp.common.util.DataStoreManager
import com.example.ddinerapp.commons.MainCoroutineRule
import com.example.ddinerapp.featureHome.domain.PlacedOrdersUseCases
import com.example.ddinerapp.featureHome.domain.model.Order
import com.example.ddinerapp.featureHome.domain.placedOrdersUseCases.CompleteOrderUseCase
import com.example.ddinerapp.featureHome.domain.placedOrdersUseCases.DeskCompletedOrdersUseCase
import com.google.common.truth.Truth.assertThat
import io.mockk.coEvery
import io.mockk.every
import io.mockk.mockk
import io.mockk.mockkStatic
import java.io.File
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.test.runTest
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

    private var order = Order(
        id = "12345678901234"
    )

    @Before
    fun setUp() {
        coEvery { store.businessCnpj } returns flow {
            emit("12345678901234")
        }
        coEvery { store.deskId } returns flow {
            emit("12222222222222")
        }
        coEvery { store.orderId } returns flow {
            emit("12345678901234")
        }
    }

    @Test
    fun getOrders() = runTest {
        coEvery {
            deskCompletedOrdersUseCase.getCompletedOrders(
                cnpj = "12345678901234",
                deskId = "12222222222222"
            )
        } returns flow {
            emit(ApiResult.Success(order))
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
        assertThat(placedOrdersViewModel.orders[0].concluded).isFalse()
        assertThat(placedOrdersViewModel.orders).contains(order)
    }

    @Test
    fun completeOrderAtTime() = runTest {
        coEvery {
            deskCompletedOrdersUseCase.getCompletedOrders(
                cnpj = "12345678901234",
                deskId = "12222222222222"
            )
        } returns flow {
            emit(ApiResult.Success(order))
            order = Order(
                id = "12345678901234",
                concluded = true
            )
            emit(ApiResult.Success(order))
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
        placedOrdersViewModel.completeOrderAtTime(System.currentTimeMillis())
        assertThat(placedOrdersViewModel.orders[0].concluded).isTrue()
    }

    @Test
    fun writeDoc() {
        val pdfDocument: PdfDocument = mockk(relaxed = true)
        val context: Context = mockk(relaxed = true)
        mockkStatic(Environment::class)
        every { Environment.getExternalStorageDirectory() } returns File("/mock/path")

        placedOrdersViewModel =
            PlacedOrdersViewModel(
                storeManager = store,
                placedOrdersUseCases = mockk(relaxed = true)
            )
        placedOrdersViewModel.writeDoc(pdfDocument, context)
    }
}
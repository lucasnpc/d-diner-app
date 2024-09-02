package com.example.ddinerapp.featureHome.presentation.placedOrders

import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onAllNodesWithTag
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.onNodeWithText
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.ddinerapp.common.data.session.getDDinerSessionForTesting
import com.example.ddinerapp.common.theme.DDinerAppTheme
import com.example.ddinerapp.featureHome.data.repositories.FakeCompleteOrderRepository
import com.example.ddinerapp.featureHome.data.repositories.FakeDeskCompletedOrdersRepository
import com.example.ddinerapp.featureHome.data.repositories.FakeDeskCompletedOrdersRepository.Companion.FAKE_DESK_DELAY
import com.example.ddinerapp.featureHome.domain.PlacedOrdersUseCases
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@HiltAndroidTest
@RunWith(AndroidJUnit4::class)
class CompletedOrdersScreenKtTest {

    @get:Rule(order = 0)
    val hiltRule = HiltAndroidRule(this)

    @get:Rule(order = 1)
    val composeTestRule = createComposeRule()

    private lateinit var placedOrdersViewModel: PlacedOrdersViewModel

    @Before
    fun setup() {
        placedOrdersViewModel = PlacedOrdersViewModel(
            session = getDDinerSessionForTesting(), placedOrdersUseCases = PlacedOrdersUseCases(
                deskCompletedOrdersUseCase = FakeDeskCompletedOrdersRepository(),
                completeOrderUseCase = FakeCompleteOrderRepository()
            )
        )
        hiltRule.inject()
        composeTestRule.setContent {
            DDinerAppTheme {
                CompletedOrdersScreen(placedOrdersViewModel)
            }
        }
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun completedOrdersScreen() = runTest {
        composeTestRule.onNodeWithTag("circularProgress").assertExists()

        composeTestRule.waitUntil(timeoutMillis = FAKE_DESK_DELAY) {
            composeTestRule.onAllNodesWithTag("circularProgress").fetchSemanticsNodes().isEmpty()
        }

        composeTestRule.onNodeWithTag("circularProgress").assertDoesNotExist()
        composeTestRule.onNodeWithText("Pedidos Feitos").assertExists()
        composeTestRule.onNodeWithText("Pedido concluído").assertExists()
        composeTestRule.onNodeWithText("Ver detalhes do pedido").assertExists()
    }
}
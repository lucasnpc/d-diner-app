package com.example.ddinerapp.featureHome.presentation.placedOrders

import androidx.compose.ui.test.junit4.createComposeRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.ddinerapp.common.data.session.getDDinerSessionForTesting
import com.example.ddinerapp.common.theme.DDinerAppTheme
import com.example.ddinerapp.featureHome.data.repositories.FakeCompleteOrderRepository
import com.example.ddinerapp.featureHome.data.repositories.FakeDeskCompletedOrdersRepository
import com.example.ddinerapp.featureHome.domain.PlacedOrdersUseCases
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
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

    @Test
    fun completedOrdersScreen() {
    }
}
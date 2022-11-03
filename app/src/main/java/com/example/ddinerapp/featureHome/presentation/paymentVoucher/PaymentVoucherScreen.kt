package com.example.ddinerapp.featureHome.presentation.paymentVoucher

import android.app.Activity
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import com.example.ddinerapp.common.util.cleanPlacedItemsString
import com.example.ddinerapp.common.util.toShowDateFormat
import com.example.ddinerapp.featureHome.presentation.paymentVoucher.components.PaymentVoucher
import com.example.ddinerapp.featureHome.presentation.paymentVoucher.components.RealizedPaymentInfo
import com.example.ddinerapp.featureHome.presentation.paymentVoucher.components.RedRealizedPayment
import com.example.ddinerapp.featureHome.presentation.paymentVoucher.components.SeeVoucherBt
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun PaymentVoucherScreen(time: Long, paymentWay: String, total: Double, items: String) {
    val context = (LocalContext.current as? Activity)
    val drawerState = rememberBottomDrawerState(BottomDrawerValue.Closed)
    val scope = rememberCoroutineScope()
    val placedItems = mutableMapOf<String, Double>()

    placedItems.putAll(
        items.cleanPlacedItemsString().map {
            it.split("=").first() to it.split("=").last().toDouble()
        }
    )

    BackHandler {
        context?.finish()
    }
    BottomDrawer(
        drawerState = drawerState,
        drawerContent = {
            PaymentVoucher(
                time.toShowDateFormat(),
                paymentWay,
                total,
                placedItems,
                closeClick = { scope.launch { drawerState.close() } },
                shareClick = {})
        }) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
        ) {
            Card(
                modifier = Modifier
                    .fillMaxSize()
                    .background(Color.White)
            ) {
                Column {
                    RedRealizedPayment()
                    Spacer(modifier = Modifier.height(32.dp))
                    RealizedPaymentInfo(time, paymentWay, total)
                    Spacer(modifier = Modifier.height(32.dp))
                    Divider(modifier = Modifier.height(2.dp))
                    SeeVoucherBt {
                        scope.launch {
                            drawerState.open()
                        }
                    }
                }
            }
        }
    }
}
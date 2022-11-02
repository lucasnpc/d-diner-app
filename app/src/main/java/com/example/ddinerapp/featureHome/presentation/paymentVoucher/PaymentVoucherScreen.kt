package com.example.ddinerapp.featureHome.presentation.paymentVoucher

import android.app.Activity
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.Card
import androidx.compose.material.Divider
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import com.example.ddinerapp.featureHome.presentation.paymentVoucher.components.RealizedPaymentInfo
import com.example.ddinerapp.featureHome.presentation.paymentVoucher.components.RedRealizedPayment
import com.example.ddinerapp.featureHome.presentation.paymentVoucher.components.SeeVoucherBt

@Composable
fun PaymentVoucherScreen(time: Long, paymentWay: String, total: Double) {
    val context = (LocalContext.current as? Activity)

    BackHandler {
        context?.finish()
    }
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

                }
            }
        }
    }
}
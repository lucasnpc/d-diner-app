package com.example.ddinerapp.featureHome.presentation.paymentVoucher.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.ddinerapp.common.util.currencyFormat
import com.example.ddinerapp.common.util.toShowDateFormat

@Composable
fun RealizedPaymentInfo(time: Long, paymentWay: String, total: Double) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .fillMaxWidth()
            .height(300.dp),
        verticalArrangement = Arrangement.SpaceBetween
    ) {
        Text(text = time.toShowDateFormat(), fontSize = 20.sp)
        Text(text = paymentWay.uppercase(), fontWeight = FontWeight.Bold, fontSize = 20.sp)
        Text(text = total.currencyFormat(), fontSize = 20.sp)
    }
}
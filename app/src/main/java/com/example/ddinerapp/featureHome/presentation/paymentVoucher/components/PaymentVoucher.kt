package com.example.ddinerapp.featureHome.presentation.paymentVoucher.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

@Composable
fun PaymentVoucher(
    time: String,
    paymentWay: String,
    total: Double,
    placedItems: MutableMap<String, Double>,
    closeClick: () -> Unit,
    shareClick: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
    ) {
        RedVoucherToolbar(closeClick = { closeClick() }, shareClick = { shareClick() })
        PaymentVoucherInfo(time, paymentWay, total, placedItems)
    }
}
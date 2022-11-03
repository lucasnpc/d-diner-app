package com.example.ddinerapp.featureHome.presentation.paymentVoucher.components

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.ddinerapp.R
import com.example.ddinerapp.common.util.currencyFormat

@Composable
fun PaymentVoucherInfo(
    time: String,
    paymentWay: String,
    total: Double,
    placedItems: MutableMap<String, Double>
) {
    LazyColumn(modifier = Modifier.padding(16.dp)) {
        item {
            Text(
                text = stringResource(R.string.payment_voucher),
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold
            )
        }
        item {
            Spacer(modifier = Modifier.height(32.dp))
            Text(text = time, fontSize = 18.sp)
        }
        item {
            Spacer(modifier = Modifier.height(32.dp))
            Text(text = "PEDIDO:", fontWeight = FontWeight.Bold, fontSize = 18.sp)
        }
        items(placedItems.toList()) { item ->
            Row(
                horizontalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(text = item.first, fontWeight = FontWeight.Bold, fontSize = 18.sp)
                Text(text = "Qtd. ${item.second}", fontSize = 18.sp)
            }
        }
        item {
            Spacer(modifier = Modifier.height(32.dp))
            Row(
                horizontalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(text = "VALOR", fontWeight = FontWeight.Bold, fontSize = 18.sp)
                Text(text = total.currencyFormat(), fontSize = 18.sp)
            }
        }
        item {
            Spacer(modifier = Modifier.height(8.dp))
            Row(
                horizontalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(text = "PAGAMENTO", fontWeight = FontWeight.Bold, fontSize = 18.sp)
                Text(text = paymentWay, fontSize = 18.sp)
            }
        }
    }
}
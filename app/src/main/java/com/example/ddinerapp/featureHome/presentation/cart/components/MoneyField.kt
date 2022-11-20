package com.example.ddinerapp.featureHome.presentation.cart.components

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import com.example.ddinerapp.common.util.currencyFormat


@Composable
fun MoneyField(total: Double, disableButton: (Double) -> Unit) {
    var cashChange by remember { mutableStateOf("") }
    val sum = if (cashChange.isNotEmpty()) cashChange.replace(",", ".").toDouble() - total else 0.0

    Spacer(modifier = Modifier.height(8.dp))
    Row(horizontalArrangement = Arrangement.Center, modifier = Modifier.fillMaxWidth()) {
        TextField(
            value = cashChange,
            onValueChange = {
                cashChange = it
                if (cashChange.isNotEmpty())
                    disableButton(cashChange.replace(",", ".").toDouble())
            },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Decimal),
            modifier = Modifier.width(130.dp)
        )
        Spacer(modifier = Modifier.width(5.dp))
        Text(
            text = "Troco ${if (sum > 0) sum.currencyFormat() else 0.0}",
            fontWeight = FontWeight.SemiBold,
            modifier = Modifier.align(Alignment.CenterVertically)
        )
    }
}
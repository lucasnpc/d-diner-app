package com.example.ddinerapp.featureHome.presentation.cart.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.ddinerapp.common.util.currencyFormat
import com.example.ddinerapp.featureHome.domain.model.MenuItem

@Composable
fun CartCard(list: List<Pair<MenuItem, Double>>, total: Double, changeState: () -> Unit) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(PaddingValues(8.dp))
    ) {
        Text(
            text = "Carrinho",
            textAlign = TextAlign.Center,
            modifier = Modifier
                .fillMaxWidth()
                .background(MaterialTheme.colors.onBackground)
                .padding(top = 16.dp, bottom = 16.dp),
            fontSize = 20.sp,
            color = Color.White
        )
        Spacer(modifier = Modifier.height(8.dp))
        Card(elevation = 8.dp) {
            Column(modifier = Modifier.padding(PaddingValues(8.dp))) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Icon(
                        imageVector = Icons.Filled.ShoppingCart,
                        contentDescription = "Shopping Cart",
                        tint = MaterialTheme.colors.primary,
                        modifier = Modifier
                            .width(32.dp)
                            .height(32.dp)
                    )
                    Text(
                        text = "Pedido em Aberto",
                        fontWeight = FontWeight.SemiBold,
                        fontSize = 20.sp
                    )
                }
                Spacer(modifier = Modifier.height(15.dp))
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "Items: ${list.sumOf { it.second }.toInt()}",
                        fontSize = 20.sp,
                        fontWeight = FontWeight.SemiBold
                    )
                    Text(
                        text = "Total: ${total.currencyFormat()}",
                        fontSize = 20.sp,
                        fontWeight = FontWeight.SemiBold
                    )
                    IconButton(onClick = { /*TODO*/ }) {
                        Icon(
                            imageVector = Icons.Filled.Info,
                            contentDescription = "Info Icon",
                            tint = MaterialTheme.colors.primary
                        )
                    }
                }
            }
        }
        Button(
            onClick = { changeState() },
            modifier = Modifier
                .fillMaxWidth()
                .border(
                    3.dp,
                    MaterialTheme.colors.primary,
                    RoundedCornerShape(bottomStart = 5.dp, bottomEnd = 5.dp)
                ),
            colors = ButtonDefaults.buttonColors(
                backgroundColor = MaterialTheme.colors.primary,
                contentColor = MaterialTheme.colors.onPrimary
            )
        ) {
            Text(text = "Pagar", textAlign = TextAlign.Center, fontSize = 20.sp)
        }
    }
}
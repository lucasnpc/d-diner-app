package com.example.ddinerapp.featureMain.presentation.orders

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Card
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.ddinerapp.featureMain.domain.model.Order
import com.example.ddinerapp.featureMain.presentation.utils.LoadingScreen

@Composable
fun OrdersScreen(viewModel: OrdersViewModel = hiltViewModel()) {
    val orders = viewModel.orders.filter { it.concluded }

    when {
        viewModel.loading.value -> {
            LoadingScreen()
        }
        orders.isEmpty() -> {}
        orders.isNotEmpty() -> {
            OrdersList(orders)
        }
    }
}

@OptIn(ExperimentalFoundationApi::class, ExperimentalMaterialApi::class)
@Composable
private fun OrdersList(orders: List<Order>) {
    Box(modifier = Modifier.padding(PaddingValues(8.dp))) {
        LazyColumn(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            stickyHeader {
                Text(
                    text = "Pedidos Feitos",
                    textAlign = TextAlign.Center,
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(MaterialTheme.colors.onBackground)
                        .padding(top = 16.dp, bottom = 16.dp)
                        .align(Alignment.TopCenter),
                    fontSize = 20.sp,
                    color = Color.White
                )
            }
            items(orders) { order ->
                Card(elevation = 8.dp, modifier = Modifier.fillMaxWidth(), onClick = {}) {
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        modifier = Modifier.padding(
                            PaddingValues(8.dp)
                        )
                    ) {
                        Text(
                            text = if (order.concluded) "Pedido concluído" else "Pedido em aberto",
                            fontSize = 18.sp,
                            fontWeight = FontWeight.SemiBold,
                            textAlign = TextAlign.Center
                        )
                        Spacer(modifier = Modifier.height(5.dp))
                        Text(text = "Ver detalhes do pedido", fontSize = 17.sp)
                    }
                }
            }
        }
    }
}
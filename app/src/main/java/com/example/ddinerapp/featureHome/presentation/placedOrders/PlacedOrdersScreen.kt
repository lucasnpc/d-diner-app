package com.example.ddinerapp.featureHome.presentation.placedOrders

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
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
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.ddinerapp.R
import com.example.ddinerapp.common.util.LoadingScreen
import com.example.ddinerapp.featureHome.domain.model.Order

@Composable
fun CompletedOrdersScreen(viewModel: PlacedOrdersViewModel = hiltViewModel()) {
    val orders = viewModel.orders.filter { it.concluded }

    when {
        viewModel.loading -> {
            LoadingScreen()
        }

        else -> {
            CompletedOrdersList(orders)
        }
    }
}

@OptIn(ExperimentalFoundationApi::class, ExperimentalMaterialApi::class)
@Composable
private fun CompletedOrdersList(orders: List<Order>) {
    Box(modifier = Modifier.padding(PaddingValues(8.dp))) {
        LazyColumn(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            stickyHeader {
                Text(
                    text = stringResource(R.string.completed_orders),
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
                            text = stringResource(R.string.completed_order),
                            fontSize = 18.sp,
                            fontWeight = FontWeight.SemiBold,
                            textAlign = TextAlign.Center
                        )
                        Spacer(modifier = Modifier.height(5.dp))
                        Text(text = stringResource(R.string.see_order_details), fontSize = 17.sp)
                    }
                }
            }
        }
    }
}
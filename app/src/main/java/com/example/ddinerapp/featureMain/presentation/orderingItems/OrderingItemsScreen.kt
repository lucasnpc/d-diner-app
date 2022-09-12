package com.example.ddinerapp.featureMain.presentation.orderingItems

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Done
import androidx.compose.material.icons.filled.Fastfood
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.ddinerapp.featureMain.presentation.home.HomeViewModel
import com.example.ddinerapp.featureMain.presentation.utils.Screen

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun OrderingItemsScreen(
    navController: NavController,
    itemCategory: String?,
    viewModel: HomeViewModel,
    desk: String
) {
    val list = viewModel.items.filter { it.category == itemCategory }
    val desk = viewModel.desks.first { it.description == desk }

    val orderedItems: MutableMap<String, Double> = mutableMapOf()

    Box(modifier = Modifier.padding(PaddingValues(8.dp))) {
        LazyColumn(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            stickyHeader {
                Text(
                    text = itemCategory.toString(),
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
            items(list) { item ->
                var itemQuantity by remember { mutableStateOf(0) }
                Card(elevation = 8.dp) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(top = 8.dp, bottom = 8.dp, start = 16.dp, end = 16.dp),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Icon(
                            imageVector = Icons.Filled.Fastfood,
                            contentDescription = "Item icon",
                            modifier = Modifier
                                .height(45.dp)
                                .width(45.dp)
                        )
                        Column {
                            Text(text = item.description, fontSize = 18.sp)
                            Text(text = "R$ ${item.price}", fontSize = 18.sp)
                        }
                        Column {
                            Row {
                                OutlinedButton(
                                    onClick = {
                                        if (itemQuantity > 0) {
                                            itemQuantity--
                                            orderedItems[item.id] = itemQuantity.toDouble()
                                        }
                                    },
                                    modifier = Modifier.width(40.dp),
                                    colors = ButtonDefaults.buttonColors(
                                        backgroundColor = MaterialTheme.colors.onBackground,
                                        contentColor = Color.White
                                    )
                                ) {
                                    Text(text = "-")
                                }
                                OutlinedButton(
                                    onClick = {},
                                    modifier = Modifier.width(40.dp),
                                    colors = ButtonDefaults.buttonColors(
                                        backgroundColor = MaterialTheme.colors.onBackground,
                                        contentColor = Color.White
                                    )
                                ) {
                                    Text(text = itemQuantity.toString())
                                }
                                OutlinedButton(
                                    onClick = {
                                        itemQuantity++
                                        orderedItems[item.id] =
                                            itemQuantity.toDouble()
                                    },
                                    modifier = Modifier.width(40.dp),
                                    colors = ButtonDefaults.buttonColors(
                                        backgroundColor = MaterialTheme.colors.onBackground,
                                        contentColor = Color.White
                                    )

                                ) {
                                    Text(text = "+")
                                }
                            }
                        }
                    }
                }
            }
        }
        ExtendedFloatingActionButton(
            text = { Text(text = "Concluir") },
            onClick = {
                viewModel.createOrderItem()
                navController.popBackStack(
                    Screen.OrderingMenuScreen.route,
                    inclusive = false
                )
            },
            modifier = Modifier.align(Alignment.BottomEnd),
            icon = {
                Icon(
                    imageVector = Icons.Filled.Done,
                    contentDescription = "Create Order Icon"
                )
            },
            backgroundColor = Color(0xFF01DC34),
            contentColor = Color.White
        )
    }
}
package com.example.ddinerapp.featureMain.presentation.orderingItems

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Done
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.ddinerapp.featureMain.domain.model.MenuItem
import com.example.ddinerapp.featureMain.presentation.utils.LoadingScreen
import com.example.ddinerapp.featureMain.presentation.utils.Screen

@Composable
fun OrderingItemsScreen(
    navController: NavController,
    itemCategory: String,
    viewModel: MenuItemViewModel = hiltViewModel()
) {
    val list = viewModel.items.filter { it.category == itemCategory }

    val orderedItems: MutableMap<String, Double> = mutableMapOf()

    when {
        viewModel.loading.value -> {
            LoadingScreen()
        }
        list.isEmpty() -> {}
        list.isNotEmpty() -> {
            ItemsList(itemCategory, list, orderedItems, navController) {
                viewModel.placeOrder(it)
            }
        }
    }

}

@OptIn(ExperimentalFoundationApi::class)
@Composable
private fun ItemsList(
    itemCategory: String?,
    list: List<MenuItem>,
    orderedItems: MutableMap<String, Double>,
    navController: NavController,
    placeOrder: (Map<String, Double>) -> Unit
) {
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
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
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
                                    modifier = Modifier.width(60.dp),
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
                placeOrder(orderedItems.filter { it.value > 0 })
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

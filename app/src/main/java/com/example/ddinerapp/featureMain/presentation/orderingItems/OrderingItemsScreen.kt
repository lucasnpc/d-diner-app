package com.example.ddinerapp.featureMain.presentation.orderingItems

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Fastfood
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.ddinerapp.featureMain.presentation.orderingMenu.items

@Composable
fun OrderingItemsScreen(navController: NavController, itemCategory: String?) {
    val itemsByCategory = items.filter { it.category == itemCategory }

    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        contentPadding = PaddingValues(8.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        items(itemsByCategory) { item ->
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
                                onClick = { if (itemQuantity > 0) itemQuantity-- },
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
                                onClick = { itemQuantity++ },
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
}
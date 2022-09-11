package com.example.ddinerapp.featureMain.presentation.orderingMenu

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.ddinerapp.featureMain.presentation.home.HomeViewModel
import com.example.ddinerapp.featureMain.presentation.utils.Screen

@Composable
fun OrderingMenuScreen(navController: NavController, viewModel: HomeViewModel) {
    val itemsCategory = viewModel.items.distinctBy { it.category }

    LazyVerticalGrid(
        columns = GridCells.Fixed(2),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        items(itemsCategory) { item ->
            OutlinedButton(
                onClick = { navController.navigate(Screen.OrderingItemsScreen.route + "/${item.category}") },
                modifier = Modifier
                    .width(120.dp)
                    .height(120.dp)
                    .padding(4.dp),
                colors = ButtonDefaults.buttonColors(backgroundColor = MaterialTheme.colors.background)
            ) {
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Icon(
                        imageVector = when (item.category) {
                            "Pizzas" -> Icons.Filled.LocalPizza
                            "Lanches" -> Icons.Filled.LunchDining
                            "Pratos" -> Icons.Filled.BrunchDining
                            "Bebidas" -> Icons.Filled.LocalDrink
                            "Sobremesas" -> Icons.Filled.Cake
                            else -> Icons.Filled.DinnerDining
                        },
                        contentDescription = "Select Menu Item Category",
                        modifier = Modifier
                            .width(30.dp)
                            .height(30.dp)
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(text = item.category, fontSize = 16.sp)
                }
            }
        }
    }
}
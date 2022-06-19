package com.example.ddinerapp.featureMain.presentation.orderingMenu

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.GridCells
import androidx.compose.foundation.lazy.LazyVerticalGrid
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.ddinerapp.featureMain.domain.model.MenuItem
import com.example.ddinerapp.featureMain.presentation.utils.Screen

val items: List<MenuItem> = listOf(
    MenuItem(price = 10.0, description = "lanche banana", category = "Lanches"),
    MenuItem(price = 10.0, description = "prato", category = "Pratos"),
    MenuItem(price = 10.0, description = "coca", category = "Bebidas"),
    MenuItem(price = 10.0, description = "lanche x-salada", category = "Lanches"),
    MenuItem(price = 10.0, description = "bife", category = "Pratos"),
    MenuItem(price = 10.0, description = "lanche x-bacon", category = "Lanches"),
    MenuItem(price = 10.0, description = "fanta", category = "Bebidas"),
    MenuItem(price = 10.0, description = "batata", category = "Porções"),
    MenuItem(price = 10.0, description = "banana", category = "Porções"),
    MenuItem(price = 10.0, description = "filé", category = "Pratos"),
    MenuItem(price = 10.0, description = "guaraná", category = "Bebidas"),
    MenuItem(price = 10.0, description = "bolo", category = "Sobremesas"),
)

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun OrderingMenuScreen(navController: NavController) {
    val itemsCategory = items.distinctBy { it.category }
    LazyVerticalGrid(
        cells = GridCells.Fixed(2),
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
package com.example.ddinerapp.featureMain.presentation.orderingMenu

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.ddinerapp.R
import com.example.ddinerapp.featureMain.domain.model.MenuItem
import com.example.ddinerapp.featureMain.presentation.orderingItems.MenuItemViewModel
import com.example.ddinerapp.featureMain.presentation.utils.LoadingScreen
import com.example.ddinerapp.featureMain.presentation.utils.Screen

@Composable
fun OrderingMenuScreen(
    navController: NavController,
    viewModel: MenuItemViewModel = hiltViewModel()
) {
    val itemsCategory = viewModel.items.distinctBy { it.category }

    when {
        viewModel.loading.value -> {
            LoadingScreen()
        }
        itemsCategory.isEmpty() -> {}
        itemsCategory.isNotEmpty() -> {
            MenuItemsCategoryList(itemsCategory, navController)
        }
    }

}

@Composable
private fun MenuItemsCategoryList(
    itemsCategory: List<MenuItem>,
    navController: NavController
) {
    LazyVerticalGrid(
        columns = GridCells.Fixed(2),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        items(itemsCategory) { item ->
            var itemLabel = item.category
            var icon = Icons.Filled.DinnerDining
            when (item.category) {
                stringResource(R.string.pizzas_category) -> {
                    itemLabel = stringResource(id = R.string.make_your_pizza)
                    icon = Icons.Filled.LocalPizza
                }
                stringResource(R.string.lanches_category) -> icon = Icons.Filled.LunchDining
                stringResource(R.string.pratos_category) -> icon = Icons.Filled.BrunchDining
                stringResource(R.string.bebidas_category) -> icon = Icons.Filled.LocalDrink
                stringResource(R.string.sobremesas_category) -> icon = Icons.Filled.Cake
            }
            MenuItemButton(
                navController = navController,
                itemsCategory = item.category,
                itemLabel = itemLabel,
                icon = icon
            )
        }
    }
}

@Composable
private fun MenuItemButton(
    navController: NavController,
    itemsCategory: String,
    itemLabel: String,
    icon: ImageVector
) {
    OutlinedButton(
        onClick = { navController.navigate(Screen.OrderingItemsScreen.route + "/${itemsCategory}") },
        modifier = Modifier
            .width(120.dp)
            .height(120.dp)
            .padding(4.dp),
        colors = ButtonDefaults.buttonColors(backgroundColor = MaterialTheme.colors.background)
    ) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Icon(
                imageVector = icon,
                contentDescription = "Select Menu Item Category",
                modifier = Modifier
                    .width(30.dp)
                    .height(30.dp)
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(text = itemLabel, fontSize = 16.sp)
        }
    }
}
package com.example.ddinerapp.featureHome.presentation.orderingMenu

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.ddinerapp.R
import com.example.ddinerapp.common.util.LoadingScreen
import com.example.ddinerapp.featureHome.domain.model.MenuItem
import com.example.ddinerapp.featureHome.presentation.menuItems.MenuItemViewModel
import com.example.ddinerapp.featureHome.presentation.orderingMenu.components.MenuItemButton
import com.example.ddinerapp.featureHome.presentation.util.HomeScreen

@Composable
fun OrderingMenuScreen(
    navController: NavController,
    viewModel: MenuItemViewModel = hiltViewModel()
) {
    val itemsCategory =
        viewModel.items.distinctBy { it.category }

    when {
        viewModel.loading.value -> {
            LoadingScreen()
        }
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
        item {
            MenuItemButton(
                itemLabel = stringResource(id = R.string.make_your_pizza),
                icon = Icons.Filled.LocalPizza
            ) {
                navController.navigate(HomeScreen.MakeYourPizzaScreen.route)
            }
        }
        items(itemsCategory) { item ->
            if (!item.category.contains("Pizzas")) {
                var icon = Icons.Filled.DinnerDining
                when (item.category) {
                    stringResource(R.string.lanches_category) -> icon = Icons.Filled.LunchDining
                    stringResource(R.string.pratos_category) -> icon = Icons.Filled.BrunchDining
                    stringResource(R.string.bebidas_category) -> icon = Icons.Filled.LocalDrink
                    stringResource(R.string.sobremesas_category) -> icon = Icons.Filled.Cake
                }
                MenuItemButton(
                    itemLabel = item.category,
                    icon = icon
                ) { label ->
                    navController.navigate(HomeScreen.OrderingItemsScreen.route + "/${label}")
                }
            }
        }
    }
}
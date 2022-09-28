package com.example.ddinerapp.featureHome.presentation.makeYourPizza

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ListAlt
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight.Companion.SemiBold
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.ddinerapp.R
import com.example.ddinerapp.common.theme.redOpaque
import com.example.ddinerapp.common.util.PIZZAS_CATEGORY
import com.example.ddinerapp.common.util.SWEET_PIZZAS
import com.example.ddinerapp.featureHome.domain.model.MenuItem
import com.example.ddinerapp.featureHome.presentation.makeYourPizza.components.CompletePizza
import com.example.ddinerapp.featureHome.presentation.makeYourPizza.components.PizzaAlertDialog
import com.example.ddinerapp.featureHome.presentation.menuItems.MenuItemViewModel
import com.example.ddinerapp.featureHome.presentation.util.AditionalsDrawerContent
import com.example.ddinerapp.featureHome.presentation.util.HomeScreen
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun MakeYourPizzaScreen(
    navController: NavController,
) {
    val viewmodel: MenuItemViewModel = hiltViewModel()
    var pizzasFilter by remember { mutableStateOf(PIZZAS_CATEGORY) }
    val pizzas = viewmodel.items.filter { it.category == pizzasFilter }
    val selectedPizzas = mutableMapOf<String, Double>()

    val brotinhoSelected = remember {
        mutableStateOf(false)
    }
    val sweetPizzasSelected = remember {
        mutableStateOf(false)
    }

    val drawerState = rememberBottomDrawerState(BottomDrawerValue.Closed)
    val openDialog = remember { mutableStateOf(false) }
    val scope = rememberCoroutineScope()

    pizzasFilter = if (sweetPizzasSelected.value)
        SWEET_PIZZAS
    else
        PIZZAS_CATEGORY

    BottomDrawer(drawerContent = {
        AditionalsDrawerContent { observations ->
            if (selectedPizzas.isEmpty())
                return@AditionalsDrawerContent
            viewmodel.placeOrder(selectedPizzas, observations)
            navController.popBackStack(
                HomeScreen.OrderingMenuScreen.route,
                inclusive = false
            )
        }
    }, drawerState = drawerState) {
        MakeYourPizzaContent(
            brotinhoSelected = brotinhoSelected,
            sweetPizzasSelected = sweetPizzasSelected,
            selectedPizzas = selectedPizzas,
            pizzas = pizzas,
            openDrawer = {
                scope.launch {
                    drawerState.open()
                }
            }
        ) {
            viewmodel.getItemProducts(it)
            openDialog.value = true
        }
        if (openDialog.value) {
            PizzaAlertDialog(list = viewmodel.itemProducts, dialog = openDialog)
        }
    }
}

@OptIn(ExperimentalMaterialApi::class)
@Composable
private fun MakeYourPizzaContent(
    brotinhoSelected: MutableState<Boolean>,
    sweetPizzasSelected: MutableState<Boolean>,
    selectedPizzas: MutableMap<String, Double>,
    pizzas: List<MenuItem>,
    openDrawer: () -> Unit,
    openDialog: (String) -> Unit
) {
    Box(
        modifier = Modifier
            .padding(PaddingValues(8.dp))
            .fillMaxSize(),
        contentAlignment = Alignment.TopCenter
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(
                text = stringResource(id = R.string.make_your_pizza),
                textAlign = TextAlign.Center,
                modifier = Modifier
                    .fillMaxWidth()
                    .background(MaterialTheme.colors.onBackground)
                    .padding(top = 16.dp, bottom = 16.dp),
                fontSize = 20.sp,
                color = Color.White
            )
            Spacer(modifier = Modifier.height(10.dp))

            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.Start) {
                FilterChip(
                    selected = brotinhoSelected.value,
                    onClick = { brotinhoSelected.value = !brotinhoSelected.value },
                    colors = ChipDefaults.outlinedFilterChipColors(
                        backgroundColor = redOpaque,
                        selectedBackgroundColor = MaterialTheme.colors.primary
                    )
                ) {
                    Text(
                        text = "Brotinho",
                        fontSize = 18.sp,
                        color = if (brotinhoSelected.value) Color.White else Color.Black,
                        fontWeight = SemiBold
                    )
                }
                Spacer(modifier = Modifier.width(8.dp))
                FilterChip(
                    selected = sweetPizzasSelected.value,
                    onClick = { sweetPizzasSelected.value = !sweetPizzasSelected.value },
                    colors = ChipDefaults.outlinedFilterChipColors(
                        backgroundColor = redOpaque,
                        selectedBackgroundColor = MaterialTheme.colors.primary
                    )
                ) {
                    Text(
                        text = "Pizzas Doces",
                        fontSize = 18.sp,
                        color = if (sweetPizzasSelected.value) Color.White else Color.Black,
                        fontWeight = SemiBold
                    )
                }
            }

            Spacer(modifier = Modifier.height(10.dp))

            if (brotinhoSelected.value) {
                CompletePizza(
                    pizzas = pizzas,
                    showMultipleFlavors = false,
                    selectPizza = {
                        selectedPizzas[it.first.id] = it.second
                    },
                    showPizzaInfo = { id ->
                        if (id.isNotEmpty())
                            openDialog(id)
                    }
                )
            } else {
                CompletePizza(
                    pizzas = pizzas,
                    selectPizza = {
                        selectedPizzas[it.first.id] = (1 / it.second)
                    },
                    showPizzaInfo = { id ->
                        if (id.isNotEmpty())
                            openDialog(id)
                    })
            }
        }

        ExtendedFloatingActionButton(
            text = { Text(text = "Adicionais") },
            onClick = { openDrawer() },
            modifier = Modifier.align(Alignment.BottomEnd),
            icon = {
                Icon(
                    imageVector = Icons.Filled.ListAlt,
                    contentDescription = "Create Order Icon"
                )
            },
            backgroundColor = Color(0xFF01DC34),
            contentColor = Color.White
        )
    }
}

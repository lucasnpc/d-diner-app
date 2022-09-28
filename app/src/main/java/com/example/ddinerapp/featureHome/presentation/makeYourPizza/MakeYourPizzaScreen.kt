package com.example.ddinerapp.featureHome.presentation.makeYourPizza

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.selection.selectable
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ListAlt
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
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
    val pizzas = viewmodel.items.filter { it.category == "Pizzas" }
    val selectedPizzas = mutableMapOf<String, Double>()
    val radioOptions = listOf("Completa", "Brotinho")
    val (selectedOption, onOptionSelect) = remember {
        mutableStateOf(radioOptions[0])
    }
    val drawerState = rememberBottomDrawerState(BottomDrawerValue.Closed)
    val openDialog = remember { mutableStateOf(false) }
    val scope = rememberCoroutineScope()

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
            radioOptions = radioOptions,
            selectedOption = selectedOption,
            onOptionSelect = onOptionSelect,
            selectedPizzas = selectedPizzas,
            pizzas = pizzas,
            openDrawer = {
                scope.launch {
                    drawerState.open()
                }
            },
            openDialog = {
                viewmodel.getItemProducts(it)
                openDialog.value = true
            }
        )
        if (openDialog.value) {
            PizzaAlertDialog(list = viewmodel.itemProducts, dialog = openDialog)
        }
    }
}

@Composable
private fun MakeYourPizzaContent(
    radioOptions: List<String>,
    selectedOption: String,
    onOptionSelect: (String) -> Unit,
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
                text = "Monte sua pizza",
                textAlign = TextAlign.Center,
                modifier = Modifier
                    .fillMaxWidth()
                    .background(MaterialTheme.colors.onBackground)
                    .padding(top = 16.dp, bottom = 16.dp),
                fontSize = 20.sp,
                color = Color.White
            )
            Spacer(modifier = Modifier.height(10.dp))

            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.Center) {
                radioOptions.forEach {
                    Row(
                        Modifier
                            .selectable(
                                selected = (it == selectedOption),
                                onClick = {
                                    onOptionSelect(it)
                                    selectedPizzas.clear()
                                },
                                role = Role.RadioButton
                            ),
                    ) {
                        RadioButton(
                            selected = it == selectedOption,
                            onClick = null,
                            colors = RadioButtonDefaults.colors(
                                unselectedColor = MaterialTheme.colors.onBackground,
                                selectedColor = MaterialTheme.colors.primary
                            )
                        )
                        Text(text = it, modifier = Modifier.align(Alignment.CenterVertically))
                        Spacer(modifier = Modifier.width(15.dp))
                    }
                }
            }

            Spacer(modifier = Modifier.height(10.dp))

            when (selectedOption) {
                "Completa" -> {
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
                "Brotinho" -> {
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
                }
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

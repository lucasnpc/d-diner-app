package com.example.ddinerapp.featureHome.presentation.makeYourPizza

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.selection.selectable
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Done
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.PopupProperties
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.ddinerapp.featureHome.domain.model.MenuItem
import com.example.ddinerapp.featureHome.presentation.orderingItems.MenuItemViewModel

@Composable
fun MakeYourPizzaScreen() {
    val viewmodel: MenuItemViewModel = hiltViewModel()
    val pizzas = viewmodel.items.filter { it.category == "Pizzas" }
    val selectedPizzas = remember { mutableListOf<MenuItem>() }
    val radioOptions = listOf("Completa", "Brotinho")
    val (selectedOption, onOptionSelect) = remember {
        mutableStateOf(radioOptions[0])
    }

    selectedPizzas.forEach {
        pizzas.toMutableList().remove(it)
    }

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
                    CompletePizza(pizzas) {
                        selectedPizzas.add(it)
                    }
                }
                "Brotinho" -> {
                    CompletePizza(
                        pizzas = pizzas,
                        showMultipleFlavors = false
                    ) {
                        selectedPizzas.add(it)
                    }
                }
            }
        }

        ExtendedFloatingActionButton(
            text = { Text(text = "Concluir") },
            onClick = { println(selectedPizzas.toList()) },
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

@OptIn(ExperimentalMaterialApi::class)
@Composable
private fun CompletePizza(
    pizzas: List<MenuItem>,
    showMultipleFlavors: Boolean = true,
    selectPizza: (MenuItem) -> Unit
) {
    var expanded by remember { mutableStateOf(false) }
    var selectedPizzaFlavorQuantity by remember { mutableStateOf(1) }
    var filteringPizzas: List<MenuItem>

    if (showMultipleFlavors) {
        ExposedDropdownMenuBox(
            expanded = expanded,
            onExpandedChange = { expanded = !expanded }) {
            OutlinedTextField(
                readOnly = true,
                value = selectedPizzaFlavorQuantity.toString(),
                onValueChange = { selectedPizzaFlavorQuantity = it.toInt() },
                label = { Text("Selecione quantos sabores na pizza") },
                trailingIcon = {
                    ExposedDropdownMenuDefaults.TrailingIcon(
                        expanded = expanded
                    )
                },
                colors = ExposedDropdownMenuDefaults.textFieldColors(backgroundColor = MaterialTheme.colors.onPrimary),
            )
            ExposedDropdownMenu(expanded = expanded, onDismissRequest = { expanded = false }) {
                (1..3).forEach {
                    DropdownMenuItem(onClick = {
                        selectedPizzaFlavorQuantity = it
                        expanded = false
                    }) {
                        Text(text = it.toString())
                    }
                }
            }
        }
    }

    (1..selectedPizzaFlavorQuantity).forEach {
        var expanded1 by remember { mutableStateOf(false) }
        var selectedPizza by remember { mutableStateOf("") }
        filteringPizzas =
            pizzas.filter { it.description.contains(selectedPizza, ignoreCase = true) }

        ExposedDropdownMenuBox(
            expanded = expanded1,
            onExpandedChange = { expanded1 = !expanded1 }) {
            OutlinedTextField(
                value = selectedPizza,
                onValueChange = {
                    selectedPizza = it
                    expanded1 = true
                },
                label = { Text("Sabor $it") },
                trailingIcon = {
                    ExposedDropdownMenuDefaults.TrailingIcon(
                        expanded = expanded1
                    )
                },
                colors = ExposedDropdownMenuDefaults.textFieldColors(backgroundColor = MaterialTheme.colors.onPrimary),
            )

            DropdownMenu(
                expanded = expanded1,
                onDismissRequest = { expanded1 = false },
                properties = PopupProperties(focusable = false)
            ) {
                filteringPizzas.forEach {
                    DropdownMenuItem(onClick = {
                        selectedPizza = it.description
                        expanded1 = false
                        selectPizza(it)
                    }) {
                        Text(text = it.description)
                    }
                }
            }
        }
    }
}

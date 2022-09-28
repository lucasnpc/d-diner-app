package com.example.ddinerapp.featureHome.presentation.makeYourPizza.components

import androidx.compose.foundation.layout.Arrangement.SpaceBetween
import androidx.compose.foundation.layout.Row
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Info
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment.Companion.CenterVertically
import androidx.compose.ui.Modifier
import androidx.compose.ui.window.PopupProperties
import com.example.ddinerapp.featureHome.domain.model.MenuItem

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun CompletePizza(
    pizzas: List<MenuItem>,
    showMultipleFlavors: Boolean = true,
    selectPizza: (Pair<MenuItem, Double>) -> Unit,
    showPizzaInfo: (String) -> Unit
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
        var pizzaDescription by remember { mutableStateOf("") }
        var selectedPizzaId by remember { mutableStateOf("") }
        filteringPizzas =
            pizzas.filter { it.description.contains(pizzaDescription, ignoreCase = true) }
        Row(horizontalArrangement = SpaceBetween) {
            ExposedDropdownMenuBox(
                expanded = expanded1,
                onExpandedChange = { expanded1 = !expanded1 }) {
                OutlinedTextField(
                    value = pizzaDescription,
                    onValueChange = {
                        pizzaDescription = it
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
                            pizzaDescription = it.description
                            expanded1 = false
                            selectPizza(it to selectedPizzaFlavorQuantity.toDouble())
                            selectedPizzaId = it.id
                        }) {
                            Text(text = it.description)
                        }
                    }
                }
            }
            if (selectedPizzaId.isNotEmpty())
                IconButton(
                    onClick = { showPizzaInfo(selectedPizzaId) },
                    modifier = Modifier.align(CenterVertically)
                ) {
                    Icon(
                        imageVector = Icons.Outlined.Info, contentDescription = "Pizza Info Button",
                        tint = MaterialTheme.colors.primary
                    )
                }
        }
    }
}

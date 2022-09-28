package com.example.ddinerapp.featureHome.presentation.makeYourPizza.components

import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.AlertDialog
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import com.example.ddinerapp.featureHome.domain.model.ItemProducts

@Composable
fun PizzaAlertDialog(list: List<ItemProducts>, dialog: MutableState<Boolean>) {
    AlertDialog(
        onDismissRequest = {
            dialog.value = false
        },
        title = {
            Text(text = "Ingredientes")
        },
        text = {
            LazyColumn {
                items(list) {
                    Text(it.description)
                }
            }
        },
        confirmButton = {},
        dismissButton = { }
    )

}
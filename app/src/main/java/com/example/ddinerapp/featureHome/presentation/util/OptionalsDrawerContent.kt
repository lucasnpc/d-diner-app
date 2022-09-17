package com.example.ddinerapp.featureHome.presentation.util

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
import androidx.compose.ui.Alignment.Companion.CenterVertically
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun OptionalsDrawerContent(
    additionalList: List<String> = listOf("Batata", "Catupiry", "Cheddar"),
    optionalsList: List<String> = listOf("Cebola", "Maionese"),
    callback: (String) -> Unit
) {
    var observations by remember { mutableStateOf("") }

    Column {
        Text(
            text = "Adicionais",
            textAlign = TextAlign.Center,
            modifier = Modifier
                .fillMaxWidth()
                .background(MaterialTheme.colors.onBackground)
                .align(CenterHorizontally),
            fontSize = 16.sp,
            color = Color.White
        )
        LazyRow(contentPadding = PaddingValues(start = 5.dp, end = 8.dp)) {
            items(additionalList) {
                val checkedState = remember { mutableStateOf(false) }
                Row {
                    Checkbox(
                        checked = checkedState.value,
                        onCheckedChange = { checkedState.value = it },
                        colors = CheckboxDefaults.colors(checkedColor = MaterialTheme.colors.primary)
                    )
                    Text(text = it, modifier = Modifier.align(CenterVertically))
                }
            }
        }
        Text(
            text = "Opcionais",
            textAlign = TextAlign.Center,
            modifier = Modifier
                .fillMaxWidth()
                .background(MaterialTheme.colors.onBackground)
                .align(CenterHorizontally),
            fontSize = 16.sp,
            color = Color.White
        )
        LazyRow(contentPadding = PaddingValues(start = 5.dp, end = 8.dp)) {
            items(optionalsList) {
                val checkedState = remember { mutableStateOf(false) }
                Row {
                    Checkbox(
                        checked = checkedState.value,
                        onCheckedChange = { checkedState.value = it },
                        colors = CheckboxDefaults.colors(checkedColor = MaterialTheme.colors.primary)
                    )
                    Text(text = it, modifier = Modifier.align(CenterVertically))
                }
            }
        }
        TextField(
            value = observations,
            onValueChange = {
                observations = it
            },
            label = { Text(text = "Observações") },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text),
            modifier = Modifier
                .align(CenterHorizontally)
                .width(300.dp)
        )
        Button(onClick = { callback(observations) }, Modifier.align(CenterHorizontally)) {
            Text(text = "Enviar pedido")
        }
    }
}
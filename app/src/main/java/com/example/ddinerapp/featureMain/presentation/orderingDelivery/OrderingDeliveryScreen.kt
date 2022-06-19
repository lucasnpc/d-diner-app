package com.example.ddinerapp.featureMain.presentation.orderingDelivery

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.material.TextFieldColors
import androidx.compose.material.TextFieldDefaults
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController

@Composable
fun OrderingDeliveryScreen(navController: NavController) {
    var name by remember { mutableStateOf("") }
    var phone by remember { mutableStateOf("") }
    var street by remember { mutableStateOf("") }
    var number by remember { mutableStateOf("") }
    var district by remember { mutableStateOf("") }
    var city by remember { mutableStateOf("") }
    val textFieldColor = TextFieldDefaults.outlinedTextFieldColors(backgroundColor = Color.White)
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(15.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        OutlinedTextField(
            value = name,
            onValueChange = { name = it },
            label = { Text(text = "Nome") },
            modifier = Modifier.fillMaxWidth(),
            colors = textFieldColor
        )
        OutlinedTextField(
            value = phone,
            onValueChange = { phone = it },
            label = { Text(text = "Telefone") },
            modifier = Modifier.fillMaxWidth(),
            colors = textFieldColor
        )
        OutlinedTextField(
            value = street,
            onValueChange = { street = it },
            label = { Text(text = "Rua") },
            modifier = Modifier.fillMaxWidth(),
            colors = textFieldColor
        )
        OutlinedTextField(
            value = number,
            onValueChange = { number = it },
            label = { Text(text = "Número") },
            modifier = Modifier.fillMaxWidth(),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
            colors = textFieldColor
        )
        OutlinedTextField(
            value = district,
            onValueChange = { district = it },
            label = { Text(text = "Bairro") },
            modifier = Modifier.fillMaxWidth(),
            colors = textFieldColor
        )
        OutlinedTextField(
            value = city,
            onValueChange = { city = it },
            label = { Text(text = "Cidade") },
            modifier = Modifier.fillMaxWidth(),
            colors = textFieldColor
        )
    }
}
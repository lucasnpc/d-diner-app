package com.example.ddinerapp.featureMain.presentation.orderingDelivery

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.OutlinedButton
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.ddinerapp.featureMain.presentation.utils.*

private val formFields = listOf(
    FormField(
        name = "name", label = "Nome", validators = listOf(TextValidator.Required)
    ),
    FormField(
        name = "phone", label = "Telefone", validators = listOf(TextValidator.Required)
    ),
    FormField(
        name = "street", label = "Rua", validators = listOf(TextValidator.Required)
    ),
    FormField(
        name = "number", label = "Número", validators = listOf(TextValidator.Required)
    ),
    FormField(
        name = "district", label = "Bairro", validators = listOf(TextValidator.Required)
    ),
    FormField(
        name = "city", label = "Cidade", validators = listOf(TextValidator.Required)
    )
)

@Composable
fun OrderingDeliveryScreen(navController: NavController) {
    val state by remember { mutableStateOf(FormState()) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(15.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Form(state = state, fields = formFields)
        OutlinedButton(
            onClick = {
                if (state.validate())
                    navController.navigate(Screen.HomeScreen.route) {
                        popUpTo(Screen.OrderingTypeScreen.route)
                    }
            },
            modifier = Modifier.align(Alignment.End),
            colors = ButtonDefaults.buttonColors(
                backgroundColor = Color(0xFF01DC34),
                contentColor = Color.White
            )
        ) {
            Text(text = "Avançar")
        }
    }
}
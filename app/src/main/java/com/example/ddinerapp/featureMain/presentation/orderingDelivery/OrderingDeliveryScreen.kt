package com.example.ddinerapp.featureMain.presentation.orderingDelivery

import android.content.Intent
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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.ddinerapp.featureHome.presentation.HomeActivity
import com.example.ddinerapp.featureMain.presentation.utils.Form
import com.example.ddinerapp.featureMain.presentation.utils.FormField
import com.example.ddinerapp.featureMain.presentation.utils.FormState
import com.example.ddinerapp.featureMain.presentation.utils.TextValidator

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
    val context = LocalContext.current

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(15.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Form(state = state, fields = formFields)
        OutlinedButton(
            onClick = {
                val deliveryData: ArrayList<String> = ArrayList()
                state.fields.forEach {
                    deliveryData.add(it.text)
                }
                if (state.validate())
                    context.startActivity(Intent(context, HomeActivity::class.java))
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
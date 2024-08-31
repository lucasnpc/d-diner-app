package com.example.ddinerapp.featureStartOrder.presentation.orderingDelivery

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
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.ddinerapp.common.util.LoadingScreen
import com.example.ddinerapp.featureHome.presentation.HomeActivity
import com.example.ddinerapp.featureStartOrder.presentation.orderingDesks.DesksViewModel
import com.example.ddinerapp.featureStartOrder.presentation.utils.Form
import com.example.ddinerapp.featureStartOrder.presentation.utils.FormField
import com.example.ddinerapp.featureStartOrder.presentation.utils.FormState
import com.example.ddinerapp.featureStartOrder.presentation.utils.TextValidator

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
fun OrderingDeliveryScreen(
    viewModel: DesksViewModel = hiltViewModel()
) {
    val deliveryDesk = viewModel.desks.find { it.description == "Delivery" }

    when {
        viewModel.loading.value -> {
            LoadingScreen()
        }
        else -> {
            deliveryDesk?.let {
                DeliveryAdressForm {
                    viewModel.selectDesk(it)
                }
            }
        }
    }
}

@Composable
private fun DeliveryAdressForm(selectDesk: () -> Unit) {
    val state by remember { mutableStateOf(FormState()) }
    state.fields = formFields
    val context = LocalContext.current

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(15.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Form(state = state)
        OutlinedButton(
            onClick = {
                if (state.validate()) {
                    val deliveryData = arrayListOf<String>()
                    state.fields.forEach {
                        deliveryData.add(it.text)
                        it.text = ""
                    }
                    selectDesk()
                    context.startActivity(Intent(context, HomeActivity::class.java))
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
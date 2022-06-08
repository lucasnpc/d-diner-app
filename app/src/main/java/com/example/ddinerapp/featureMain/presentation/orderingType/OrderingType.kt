package com.example.ddinerapp.featureMain.presentation.orderingType

import androidx.compose.foundation.layout.*
import androidx.compose.material.Button
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DeliveryDining
import androidx.compose.material.icons.filled.Dining
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.ddinerapp.featureMain.presentation.utils.OrderingScreens

@Composable
fun OrderingType(navController: NavController) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 30.dp),
        horizontalArrangement = Arrangement.SpaceAround
    ) {
        Button(
            onClick = { navController.navigate(OrderingScreens.OrderingDesksScreen.route) },
            modifier = Modifier
                .width(164.dp)
                .height(120.dp)
        ) {
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                Icon(imageVector = Icons.Filled.Dining, contentDescription = "Local Dining Button")
                Text(text = "Mesa")
            }
        }
        Button(
            onClick = { navController.navigate(OrderingScreens.OrderingDeliveryScreen.route) },
            modifier = Modifier
                .width(164.dp)
                .height(120.dp)
        ) {
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                Icon(
                    imageVector = Icons.Filled.DeliveryDining,
                    contentDescription = "Delivery Dining Button"
                )
                Text(text = "Delivery")
            }
        }
    }
}
package com.example.ddinerapp.featureMain.presentation.orderingType

import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.DeliveryDining
import androidx.compose.material.icons.rounded.Restaurant
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.ddinerapp.featureMain.presentation.utils.Screen

@Composable
fun OrderingTypeScreen(navController: NavController) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 30.dp),
        horizontalArrangement = Arrangement.SpaceAround
    ) {
        Button(
            onClick = { navController.navigate(Screen.OrderingDesksScreen.route) },
            modifier = Modifier
                .width(164.dp)
                .height(120.dp),
            colors = ButtonDefaults.buttonColors(backgroundColor = MaterialTheme.colors.background)
        ) {
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                Icon(
                    imageVector = Icons.Rounded.Restaurant,
                    contentDescription = "Local Dining",
                    modifier = Modifier
                        .width(30.dp)
                        .height(30.dp),
                    tint = MaterialTheme.colors.primary
                )
                Spacer(modifier = Modifier.height(5.dp))
                Text(text = "Mesa", fontSize = 16.sp)
            }
        }
        Button(
            onClick = { navController.navigate(Screen.OrderingDeliveryScreen.route) },
            modifier = Modifier
                .width(164.dp)
                .height(120.dp),
            colors = ButtonDefaults.buttonColors(backgroundColor = MaterialTheme.colors.background)
        ) {
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                Icon(
                    imageVector = Icons.Rounded.DeliveryDining,
                    contentDescription = "Delivery Dining",
                    modifier = Modifier
                        .width(30.dp)
                        .height(30.dp),
                    tint = MaterialTheme.colors.primary
                )
                Spacer(modifier = Modifier.height(5.dp))
                Text(text = "Delivery", fontSize = 16.sp)
            }
        }
    }
}
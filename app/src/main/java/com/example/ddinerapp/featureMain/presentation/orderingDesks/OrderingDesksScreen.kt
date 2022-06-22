package com.example.ddinerapp.featureMain.presentation.orderingDesks

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.GridCells
import androidx.compose.foundation.lazy.LazyVerticalGrid
import androidx.compose.foundation.lazy.items
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Icon
import androidx.compose.material.OutlinedButton
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.LocalDining
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.ddinerapp.featureMain.domain.model.Desk
import com.example.ddinerapp.featureMain.presentation.utils.Screen

val desks: List<Desk> = listOf(
    Desk(id = 1, name = "Mesa - 1", isOccupied = false),
    Desk(id = 2, name = "Mesa - 2", isOccupied = false),
    Desk(id = 3, name = "Mesa - 3", isOccupied = false),
    Desk(id = 4, name = "Mesa - 4", isOccupied = false),
    Desk(id = 5, name = "Mesa - 5", isOccupied = true),
    Desk(id = 6, name = "Mesa - 6", isOccupied = false),
    Desk(id = 7, name = "Mesa - 7", isOccupied = false),
    Desk(id = 8, name = "Mesa - 8", isOccupied = true),
    Desk(id = 9, name = "Mesa - 9", isOccupied = false),
)

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun OrderingDesksScreen(navController: NavController) {
    LazyVerticalGrid(
        cells = GridCells.Fixed(2),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        items(desks) { desk ->
            OutlinedButton(
                onClick = {
                    navController.navigate(Screen.HomeScreen.route + "/${desk.name}") {
                        popUpTo(Screen.OrderingTypeScreen.route)
                    }
                },
                modifier = Modifier
                    .width(120.dp)
                    .height(120.dp)
                    .padding(8.dp),
                colors = ButtonDefaults.buttonColors(
                    backgroundColor = if (desk.isOccupied) Color(0xFFEC4235) else Color(
                        0xFF00DD31
                    )
                )
            ) {
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Icon(
                        imageVector = Icons.Rounded.LocalDining,
                        contentDescription = "Select Table",
                        modifier = Modifier
                            .width(30.dp)
                            .height(30.dp)
                    )
                    Spacer(modifier = Modifier.height(5.dp))
                    Text(text = desk.name, fontSize = 16.sp)
                }
            }
        }
    }
}
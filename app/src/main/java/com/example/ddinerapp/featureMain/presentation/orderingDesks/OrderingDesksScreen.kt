package com.example.ddinerapp.featureMain.presentation.orderingDesks

import android.content.Intent
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.ddinerapp.common.util.LoadingScreen
import com.example.ddinerapp.featureHome.domain.model.Desk
import com.example.ddinerapp.featureHome.presentation.HomeActivity

@Composable
fun OrderingDesksScreen(
    viewModel: DesksViewModel = hiltViewModel()
) {
    val desks = viewModel.desks.filter { it.description != "Delivery" }

    when {
        viewModel.loading.value -> {
            LoadingScreen()
        }
        else -> {
            DesksList(
                desks.sortedBy { it.description },
            ) {
                viewModel.selectDesk(it)
            }
        }
    }
}

@Composable
private fun DesksList(
    desks: List<Desk>,
    selectDesk: (Desk) -> Unit
) {

    val context = LocalContext.current

    LazyVerticalGrid(
        columns = GridCells.Fixed(2),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        items(desks) { desk ->
            OutlinedButton(
                onClick = {
                    selectDesk(desk)
                    context.startActivity(Intent(context, HomeActivity::class.java))
                },
                modifier = Modifier
                    .width(120.dp)
                    .padding(8.dp),
                colors = ButtonDefaults.buttonColors(
                    backgroundColor = if (desk.isOccupied) Color(0xFFEC4235) else Color(
                        0xFF00DD31
                    )
                )
            ) {
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Text(text = if (desk.isOccupied) "Ocupada" else "Disponível", fontSize = 20.sp)
                    Spacer(modifier = Modifier.height(10.dp))
                    Icon(
                        imageVector = Icons.Rounded.LocalDining,
                        contentDescription = "Select Table",
                        modifier = Modifier
                            .width(30.dp)
                            .height(30.dp)
                    )
                    Spacer(modifier = Modifier.height(5.dp))
                    Text(text = desk.description, fontSize = 18.sp)
                }
            }
        }
    }
}
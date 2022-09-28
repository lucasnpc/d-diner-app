package com.example.ddinerapp.featureHome.presentation.orderingMenu.components

import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun MenuItemButton(
    itemLabel: String,
    icon: ImageVector,
    navigate: (category: String) -> Unit
) {
    OutlinedButton(
        onClick = { navigate(itemLabel) },
        modifier = Modifier
            .width(120.dp)
            .height(120.dp)
            .padding(4.dp),
        colors = ButtonDefaults.buttonColors(backgroundColor = MaterialTheme.colors.background)
    ) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Icon(
                imageVector = icon,
                contentDescription = "Select Menu Item Category",
                modifier = Modifier
                    .width(30.dp)
                    .height(30.dp),
                tint = MaterialTheme.colors.primary
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(text = itemLabel, fontSize = 16.sp)
        }
    }
}
package com.example.ddinerapp.featureMain.presentation.cart

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun CartScreen() {

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(PaddingValues(8.dp))
    ) {
        Text(
            text = "Carrinho",
            textAlign = TextAlign.Center,
            modifier = Modifier
                .fillMaxWidth()
                .background(MaterialTheme.colors.onBackground)
                .padding(top = 16.dp, bottom = 16.dp),
            fontSize = 20.sp,
            color = Color.White
        )

    }
}
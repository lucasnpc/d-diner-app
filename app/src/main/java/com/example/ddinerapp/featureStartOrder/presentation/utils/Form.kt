package com.example.ddinerapp.featureStartOrder.presentation.utils

import androidx.compose.runtime.Composable

@Composable
fun Form(state: FormState) {
    state.fields.forEach {
        it.Content()
    }
}
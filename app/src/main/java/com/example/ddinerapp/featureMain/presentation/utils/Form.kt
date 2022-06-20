package com.example.ddinerapp.featureMain.presentation.utils

import androidx.compose.runtime.Composable

@Composable
fun Form(state: FormState, fields: List<FormField>) {
    state.fields = fields

    fields.forEach {
        it.Content()
    }
}
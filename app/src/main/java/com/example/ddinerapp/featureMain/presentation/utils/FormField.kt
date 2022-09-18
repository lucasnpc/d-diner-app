package com.example.ddinerapp.featureMain.presentation.utils

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.material.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.KeyboardType

class FormField(val name: String, val label: String = "", val validators: List<TextValidator>) {
    var text: String by mutableStateOf("")
    private var lbl: String by mutableStateOf(label)
    var hasError: Boolean by mutableStateOf(false)


    private fun showError(error: String) {
        hasError = true
        lbl = error
    }

    private fun hideError() {
        lbl = label
        hasError = false
    }

    @Composable
    fun Content() {
        val textFieldColor =
            TextFieldDefaults.outlinedTextFieldColors(backgroundColor = Color.White)
        OutlinedTextField(
            value = text,
            onValueChange = {
                hideError()
                text = it
            },
            isError = hasError,
            label = { Text(text = lbl) },
            modifier = Modifier.fillMaxWidth(),
            colors = textFieldColor,
            keyboardOptions = when (name) {
                "number" -> KeyboardOptions(keyboardType = KeyboardType.Number)
                "phone" -> KeyboardOptions(keyboardType = KeyboardType.Phone)
                else -> KeyboardOptions(keyboardType = KeyboardType.Text)
            }
        )
    }

    fun validate(): Boolean = validators.map {
        when (it) {
            is TextValidator.Required -> {
                if (text.isEmpty()) {
                    showError(it.message)
                    return@map false
                }
                true
            }
        }
    }.all { it }
}
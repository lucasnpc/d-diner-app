package com.example.ddinerapp.featureMain.presentation.utils

private const val REQUIRED_MESSAGE = "Campo vazio"

sealed class TextValidator(val message: String) {
    object Required : TextValidator(message = REQUIRED_MESSAGE)
}

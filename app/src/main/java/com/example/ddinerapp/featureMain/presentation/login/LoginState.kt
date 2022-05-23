package com.example.ddinerapp.featureMain.presentation.login

data class LoginState(
    val isLoading: Boolean = false,
    val failed: Boolean = false,
    val success: Boolean = false
)


package com.example.ddinerapp.featureMain.domain.useCases

data class MainUseCases(
    val authenticateUserUseCase: AuthenticateUserUseCase,
    val getDesksUseCase: GetDesksUseCase
)
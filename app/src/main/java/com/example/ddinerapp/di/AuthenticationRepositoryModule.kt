package com.example.ddinerapp.di

import com.example.ddinerapp.featureAuthentication.domain.useCases.AuthenticateUserUseCase
import com.example.ddinerapp.featureAuthentication.domain.useCases.AuthenticationUseCases
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped

@Module
@InstallIn(ViewModelComponent::class)
object AuthenticationRepositoryModule {

    @ViewModelScoped
    @Provides
    fun provideAuthenticationUseCases(): AuthenticationUseCases =
        AuthenticationUseCases(authenticateUserUseCase = AuthenticateUserUseCase())
}
package com.example.ddinerapp.di

import com.example.ddinerapp.featureMain.domain.useCases.AddOrderUseCase
import com.example.ddinerapp.featureMain.domain.useCases.GetDesksUseCase
import com.example.ddinerapp.featureMain.domain.useCases.MainUseCases
import com.example.ddinerapp.featureMain.domain.useCases.SetOccupiedDeskUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped

@Module
@InstallIn(ViewModelComponent::class)
object MainRepositoryModule {

    @ViewModelScoped
    @Provides
    fun provideMainUseCases(): MainUseCases =
        MainUseCases(
            getDesksUseCase = GetDesksUseCase(),
            setOccupiedDeskUseCase = SetOccupiedDeskUseCase(),
            addOrderUseCase = AddOrderUseCase(),
        )
}
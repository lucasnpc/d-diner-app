package com.example.ddinerapp.di

import com.example.ddinerapp.featureMain.data.repository.MainRepositoryImpl
import com.example.ddinerapp.featureMain.domain.remote.MainService
import com.example.ddinerapp.featureMain.domain.repository.MainRepository
import com.example.ddinerapp.featureMain.domain.useCases.*
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
    fun provideMainRepository(service: MainService): MainRepository =
        MainRepositoryImpl(service = service)

    @ViewModelScoped
    @Provides
    fun provideMainUseCases(): MainUseCases =
        MainUseCases(
            authenticateUserUseCase = AuthenticateUserUseCase(),
            getDesksUseCase = GetDesksUseCase(),
            getMenuItemsUseCase = GetMenuItemsUseCase(),
            setOccupiedDeskUseCase = SetOccupiedDeskUseCase(),
            getDeskOrders = GetDeskOrders()
        )
}
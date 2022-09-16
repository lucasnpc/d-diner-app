package com.example.ddinerapp.di

import com.example.ddinerapp.featureHome.domain.useCases.*
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped

@Module
@InstallIn(ViewModelComponent::class)
object HomeRepositoryModule {

    @ViewModelScoped
    @Provides
    fun provideMainUseCases(): HomeUseCases =
        HomeUseCases(
            getMenuItemsUseCase = GetMenuItemsUseCase(),
            getDeskOrders = GetDeskOrders(),
            placeOrdersUseCase = PlaceOrdersUseCase(),
            getOrderedItemsUseCase = GetOrderedItemsUseCase(),
            concludeOrderUseCase = ConcludeOrderUseCase()
        )
}
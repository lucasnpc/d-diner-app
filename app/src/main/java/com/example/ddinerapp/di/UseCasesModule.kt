package com.example.ddinerapp.di

import com.example.ddinerapp.featureAuthentication.domain.useCases.AuthenticateUserUseCase
import com.example.ddinerapp.featureAuthentication.domain.useCases.AuthenticationUseCases
import com.example.ddinerapp.featureHome.data.repositories.CompleteOrderRepository
import com.example.ddinerapp.featureHome.data.repositories.DeskCompletedOrdersRepository
import com.example.ddinerapp.featureHome.data.repositories.GetMenuItemsRepository
import com.example.ddinerapp.featureHome.domain.PlacedOrdersUseCases
import com.example.ddinerapp.featureHome.domain.useCases.GetCurrentDeskOrder
import com.example.ddinerapp.featureHome.domain.useCases.GetItemProducts
import com.example.ddinerapp.featureHome.domain.useCases.GetOrderedItemsUseCase
import com.example.ddinerapp.featureHome.domain.useCases.HomeUseCases
import com.example.ddinerapp.featureHome.domain.useCases.PlaceOrdersUseCase
import com.example.ddinerapp.featureHome.domain.useCases.RegisterGainUseCase
import com.example.ddinerapp.featureStartOrder.domain.useCases.AddOrderUseCase
import com.example.ddinerapp.featureStartOrder.domain.useCases.DisoccupyDeskUseCase
import com.example.ddinerapp.featureStartOrder.domain.useCases.GetDesksUseCase
import com.example.ddinerapp.featureStartOrder.domain.useCases.MainUseCases
import com.example.ddinerapp.featureStartOrder.domain.useCases.SetOccupiedDeskUseCase
import com.google.firebase.firestore.FirebaseFirestore
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped

@Module
@InstallIn(ViewModelComponent::class)
object UseCasesModule {

    @ViewModelScoped
    @Provides
    fun provideAuthenticationUseCases(db: FirebaseFirestore): AuthenticationUseCases =
        AuthenticationUseCases(authenticateUserUseCase = AuthenticateUserUseCase(db))

    @ViewModelScoped
    @Provides
    fun provideMainUseCases(db: FirebaseFirestore): MainUseCases = MainUseCases(
        getDesksUseCase = GetDesksUseCase(db),
        setOccupiedDeskUseCase = SetOccupiedDeskUseCase(db),
        addOrderUseCase = AddOrderUseCase(db),
        disoccupyDeskUseCase = DisoccupyDeskUseCase(db)
    )

    @ViewModelScoped
    @Provides
    fun provideHomeUseCases(db: FirebaseFirestore): HomeUseCases = HomeUseCases(
        getMenuItemsUseCase = GetMenuItemsRepository(db),
        placeOrdersUseCase = PlaceOrdersUseCase(db),
        getOrderedItemsUseCase = GetOrderedItemsUseCase(db),
        getCurrentDeskOrder = GetCurrentDeskOrder(db),
        registerGainUseCase = RegisterGainUseCase(db),
        getItemProducts = GetItemProducts(db)
    )

    @ViewModelScoped
    @Provides
    fun providePlacedOrdersUseCases(db: FirebaseFirestore): PlacedOrdersUseCases =
        PlacedOrdersUseCases(
            deskCompletedOrdersUseCase = DeskCompletedOrdersRepository(db),
            completeOrderUseCase = CompleteOrderRepository(db)
        )
}
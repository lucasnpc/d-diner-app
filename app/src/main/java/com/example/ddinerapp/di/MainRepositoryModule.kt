package com.example.ddinerapp.di

import com.example.ddinerapp.featureMain.data.repository.MainRepositoryImpl
import com.example.ddinerapp.featureMain.domain.remote.MainService
import com.example.ddinerapp.featureMain.domain.repository.MainRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object MainRepositoryModule {
    @Singleton
    @Provides
    fun provideMainRepository(service: MainService): MainRepository =
        MainRepositoryImpl(service = service)
}
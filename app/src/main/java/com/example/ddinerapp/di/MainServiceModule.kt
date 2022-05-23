package com.example.ddinerapp.di

import com.example.ddinerapp.featureMain.data.source.remote.MainServiceImpl
import com.example.ddinerapp.featureMain.domain.remote.MainService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import io.ktor.client.*
import io.ktor.client.engine.android.*
import io.ktor.client.features.json.*
import io.ktor.client.features.json.serializer.*
import io.ktor.client.features.logging.*
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object MainServiceModule {
    @Singleton
    @Provides
    fun provideMainService(): MainService = MainServiceImpl(
        client = HttpClient(Android) {
            install(Logging) {
                level = LogLevel.ALL
            }
            install(JsonFeature) {
                serializer = KotlinxSerializer()
            }
        }
    )
}
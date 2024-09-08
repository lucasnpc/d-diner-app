package com.example.ddinerapp.di

import com.example.ddinerapp.common.data.logger.DDinerLogger
import com.example.ddinerapp.common.data.logger.provideDatadogLogger
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object LoggerModule {

    @Provides
    @Singleton
    fun provideDDinerLogger(): DDinerLogger {
        return provideDatadogLogger()
    }
}
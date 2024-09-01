package com.example.ddinerapp.di

import android.content.Context
import com.example.ddinerapp.common.data.session.DDinerSession
import com.example.ddinerapp.common.data.session.getDDinerSession
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object SessionModule {

    @Provides
    @Singleton
    fun provideDDinerSession(@ApplicationContext appContext: Context): DDinerSession {
        return getDDinerSession(appContext)
    }
}
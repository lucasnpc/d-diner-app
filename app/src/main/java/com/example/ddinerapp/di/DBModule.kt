package com.example.ddinerapp.di

import com.example.ddinerapp.BuildConfig
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.FirebaseFirestoreSettings
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DBModule {

    @Singleton
    @Provides
    fun provideFirestore(): FirebaseFirestore {
        val db = Firebase.firestore
        if (BuildConfig.isFirebaseLocal) {
            db.useEmulator("10.0.2.2", 8080)
            val settings = FirebaseFirestoreSettings.Builder()
                .setPersistenceEnabled(false)
                .build()
            db.firestoreSettings = settings
        }

        return db
    }
}
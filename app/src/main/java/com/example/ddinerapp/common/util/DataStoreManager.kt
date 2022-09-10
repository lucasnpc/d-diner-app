package com.example.ddinerapp.common.util

import android.content.Context
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.preferencesDataStore
import com.example.ddinerapp.common.util.DataStoreKeys.USER_ROLE
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.runBlocking
import javax.inject.Inject
import javax.inject.Singleton

private val Context.dataStore by preferencesDataStore("settings")

@Singleton
class DataStoreManager @Inject constructor(@ApplicationContext appContext: Context) {

    private val settingsDataStore = appContext.dataStore

    fun setUserRole(role: String) = runBlocking {
        settingsDataStore.edit { settings ->
            settings[USER_ROLE] = role
        }
    }

    val userRole: Flow<String> = settingsDataStore.data.map { prefs ->
        prefs[USER_ROLE] ?: ""
    }

}
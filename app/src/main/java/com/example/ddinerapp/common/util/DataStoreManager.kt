package com.example.ddinerapp.common.util

import android.content.Context
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.preferencesDataStore
import com.example.ddinerapp.common.util.DataStoreKeys.BUSINESS_CNPJ
import com.example.ddinerapp.common.util.DataStoreKeys.SELECTED_DESK_ID
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

    fun setBusinessCnpj(cnpj: String) = runBlocking {
        settingsDataStore.edit { settings ->
            settings[BUSINESS_CNPJ] = cnpj
        }
    }

    fun setSelectedDesk(deskId: String) = runBlocking {
        settingsDataStore.edit { settings ->
            settings[SELECTED_DESK_ID] = deskId
        }
    }

    fun clearDataStore() = runBlocking {
        settingsDataStore.edit {
            it.clear()
        }
    }


    val userRole: Flow<String> = settingsDataStore.data.map { prefs ->
        prefs[USER_ROLE] ?: ""
    }

    val businessCnpj: Flow<String> = settingsDataStore.data.map { prefs ->
        prefs[BUSINESS_CNPJ].toString()
    }

    val deskId: Flow<String> = settingsDataStore.data.map { prefs ->
        prefs[SELECTED_DESK_ID].toString()
    }

}
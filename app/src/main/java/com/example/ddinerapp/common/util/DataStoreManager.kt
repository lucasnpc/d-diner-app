package com.example.ddinerapp.common.util

import android.content.Context
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.preferencesDataStore
import com.example.ddinerapp.common.util.DataStoreKeys.BUSINESS_CNPJ
import com.example.ddinerapp.common.util.DataStoreKeys.CURRENT_ORDER_ID
import com.example.ddinerapp.common.util.DataStoreKeys.SELECTED_DESK_ID
import com.example.ddinerapp.common.util.DataStoreKeys.USER_CPF
import com.example.ddinerapp.common.util.DataStoreKeys.USER_NAME
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.runBlocking
import javax.inject.Inject
import javax.inject.Singleton

val Context.dataStore by preferencesDataStore("settings")

@Singleton
class DataStoreManager @Inject constructor(@ApplicationContext appContext: Context) {

    private val settingsDataStore = appContext.dataStore

    fun setBusinessCnpj(cnpj: String) = runBlocking {
        settingsDataStore.edit { settings ->
            settings[BUSINESS_CNPJ] = cnpj
        }
    }

    fun setUserInstance(userName: String, userCpf: String) = runBlocking {
        settingsDataStore.edit { settings ->
            settings[USER_NAME] = userName
            settings[USER_CPF] = userCpf
        }
    }

    fun setSelectedDesk(deskId: String) = runBlocking {
        settingsDataStore.edit { settings ->
            settings[SELECTED_DESK_ID] = deskId
        }
    }

    fun setCurrentOrder(orderId: String) = runBlocking {
        settingsDataStore.edit { settings ->
            settings[CURRENT_ORDER_ID] = orderId
        }
    }

    fun clearDataStore() = runBlocking {
        settingsDataStore.edit {
            it.clear()
        }
    }


    val businessCnpj: Flow<String> = settingsDataStore.data.map { prefs ->
        prefs[BUSINESS_CNPJ].toString()
    }

    val userCpf: Flow<String> = settingsDataStore.data.map { prefs ->
        prefs[USER_CPF] ?: ""
    }
    val userName: Flow<String> = settingsDataStore.data.map { prefs ->
        prefs[USER_NAME] ?: ""
    }

    val deskId: Flow<String> = settingsDataStore.data.map { prefs ->
        prefs[SELECTED_DESK_ID].toString()
    }

    val orderId: Flow<String> = settingsDataStore.data.map { prefs ->
        prefs[CURRENT_ORDER_ID].toString()
    }

}
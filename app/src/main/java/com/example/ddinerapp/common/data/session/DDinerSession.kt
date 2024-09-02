package com.example.ddinerapp.common.data.session

import android.content.Context
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.preferencesDataStore
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject
import javax.inject.Singleton
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.runBlocking

sealed interface DDinerSession {
    fun saveField(key: Preferences.Key<String>, field: String)
    fun getField(key: Preferences.Key<String>): Flow<String>
    suspend fun clearSessionData()
}

private val Context.dataStore by preferencesDataStore("settings")

fun getDDinerSession(@ApplicationContext appContext: Context): DDinerSession =
    DefaultDDinerSession(appContext)

fun getDDinerSessionForTesting(): DDinerSession = TestingDDinerSession

@Singleton
internal class DefaultDDinerSession @Inject constructor(@ApplicationContext appContext: Context) :
    DDinerSession {
    private val settingsDataStore = appContext.dataStore

    override fun saveField(key: Preferences.Key<String>, field: String): Unit = runBlocking {
        settingsDataStore.edit { settings ->
            settings[key] = field
        }
    }

    override fun getField(key: Preferences.Key<String>): Flow<String> =
        settingsDataStore.data.map { prefs ->
            prefs[key].toString()
        }

    override suspend fun clearSessionData() {
        settingsDataStore.edit {
            it.clear()
        }
    }

}

internal data object TestingDDinerSession : DDinerSession {
    override fun saveField(key: Preferences.Key<String>, field: String) {
        //TODO("Not yet implemented")
    }

    override fun getField(key: Preferences.Key<String>): Flow<String> {
        return flow { emit("") }
    }

    override suspend fun clearSessionData() {
        //TODO("Not yet implemented")
    }

}


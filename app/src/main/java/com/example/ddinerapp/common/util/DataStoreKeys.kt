package com.example.ddinerapp.common.util

import androidx.datastore.preferences.core.stringPreferencesKey

object DataStoreKeys {
    val USER_ROLE = stringPreferencesKey("user_role")
    val BUSINESS_CNPJ = stringPreferencesKey("business_cnpj")
}
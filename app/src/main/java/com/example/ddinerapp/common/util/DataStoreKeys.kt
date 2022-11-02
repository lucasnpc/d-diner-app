package com.example.ddinerapp.common.util

import androidx.datastore.preferences.core.stringPreferencesKey

object DataStoreKeys {
    val BUSINESS_CNPJ = stringPreferencesKey("business_cnpj")
    val USER_NAME = stringPreferencesKey("user_name")
    val USER_CPF = stringPreferencesKey("user_cpf")
    val SELECTED_DESK_ID = stringPreferencesKey("desk_id")
    val CURRENT_ORDER_ID = stringPreferencesKey("order_id")
}
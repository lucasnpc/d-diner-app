package com.example.ddinerapp.common.data.session

import androidx.datastore.preferences.core.stringPreferencesKey

object SessionPreferencesKeys {
    val PREF_BUSINESS_CNPJ = stringPreferencesKey("business_cnpj")
    val PREF_USER_NAME = stringPreferencesKey("user_name")
    val PREF_USER_CPF = stringPreferencesKey("user_cpf")
    val PREF_SELECTED_DESK_ID = stringPreferencesKey("desk_id")
    val PREF_CURRENT_ORDER_ID = stringPreferencesKey("order_id")
}
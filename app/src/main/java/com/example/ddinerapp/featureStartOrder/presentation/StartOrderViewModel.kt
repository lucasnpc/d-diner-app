package com.example.ddinerapp.featureStartOrder.presentation

import androidx.lifecycle.ViewModel
import com.example.ddinerapp.common.util.DataStoreManager
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class StartOrderViewModel @Inject constructor(
    private val storeManager: DataStoreManager
) : ViewModel() {

    fun clearPreferences() = storeManager.clearDataStore()

}
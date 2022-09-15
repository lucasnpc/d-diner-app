package com.example.ddinerapp.featureHome.presentation

import androidx.lifecycle.ViewModel
import com.example.ddinerapp.common.util.DataStoreManager
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val storeManager: DataStoreManager
) : ViewModel() {

    fun clearPreferences() = storeManager.clearDataStore()

}
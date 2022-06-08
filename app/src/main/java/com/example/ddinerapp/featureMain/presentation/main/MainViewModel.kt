package com.example.ddinerapp.featureMain.presentation.main

import androidx.lifecycle.ViewModel
import com.example.ddinerapp.common.util.DataStoreManager
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(private val store: DataStoreManager) : ViewModel() {
    fun getUserRole() = store.userRole
}
package com.example.ddinerapp.featureMain.presentation.home

import androidx.lifecycle.ViewModel
import com.example.ddinerapp.common.util.DataStoreManager
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(private val store: DataStoreManager) : ViewModel() {
    fun getUserRole() = store.userRole
}
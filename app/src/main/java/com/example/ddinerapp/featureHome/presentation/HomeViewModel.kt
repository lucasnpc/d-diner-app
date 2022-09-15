package com.example.ddinerapp.featureHome.presentation

import androidx.lifecycle.ViewModel
import com.example.ddinerapp.common.util.DataStoreManager
import com.example.ddinerapp.featureMain.domain.useCases.MainUseCases
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val store: DataStoreManager,
    private val mainUseCases: MainUseCases
) : ViewModel() {


}
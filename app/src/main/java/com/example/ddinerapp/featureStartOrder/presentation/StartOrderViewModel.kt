package com.example.ddinerapp.featureStartOrder.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.ddinerapp.common.data.session.DDinerSession
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.launch

@HiltViewModel
class StartOrderViewModel @Inject constructor(
    private val session: DDinerSession
) : ViewModel() {

    fun clearPreferences() = viewModelScope.launch {
        session.clearSessionData()
    }
}
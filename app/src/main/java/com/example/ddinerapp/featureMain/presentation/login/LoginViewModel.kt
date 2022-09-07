package com.example.ddinerapp.featureMain.presentation.login

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.map
import com.example.ddinerapp.common.util.AuthenticationState
import com.example.ddinerapp.common.util.DataStoreManager
import com.example.ddinerapp.common.util.FirebaseUserLiveData
import com.example.ddinerapp.featureMain.domain.repository.MainRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val repository: MainRepository,
    private val store: DataStoreManager
) : ViewModel() {

    private val _state = mutableStateOf(LoginState())
    val state: MutableState<LoginState> = _state

    val authenticationState = FirebaseUserLiveData().map { user ->
        if (user != null) {
            AuthenticationState.AUTHENTICATED
            //TODO Get user Info
            //store.setUserRole()
        } else
            AuthenticationState.UNAUTHENTICATED
    }

}
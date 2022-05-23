package com.example.ddinerapp.featureMain.presentation.login

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.ddinerapp.featureMain.domain.repository.MainRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(private val repository: MainRepository) : ViewModel() {

    private val _state = mutableStateOf(LoginState())
    val state: MutableState<LoginState> = _state


    fun authUser(username: String, password: String) {
        viewModelScope.launch {
            _state.value = LoginState(isLoading = true)
            repository.authUsers(username, password)
        }
    }
}
package com.example.ddinerapp.featureAuthentication.presentation.signUp

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.ddinerapp.common.data.session.DDinerSession
import com.example.ddinerapp.common.data.session.SessionPreferencesKeys.PREF_BUSINESS_CNPJ
import com.example.ddinerapp.common.data.session.SessionPreferencesKeys.PREF_USER_CPF
import com.example.ddinerapp.common.data.session.SessionPreferencesKeys.PREF_USER_NAME
import com.example.ddinerapp.common.util.AuthenticationState
import com.example.ddinerapp.common.util.CNPJ_FIELD
import com.example.ddinerapp.common.util.USERCPF_FIELD
import com.example.ddinerapp.common.util.USERNAME_FIELD
import com.example.ddinerapp.featureAuthentication.domain.useCases.AuthenticationUseCases
import com.google.firebase.auth.FirebaseUser
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

@HiltViewModel
class SignUpViewModel @Inject constructor(
    private val session: DDinerSession,
    private val authenticationUseCases: AuthenticationUseCases
) : ViewModel() {

    private val _loading = MutableStateFlow(false)
    val loading: StateFlow<Boolean> = _loading

    private val _authenticationState = MutableStateFlow(AuthenticationState.UNDEFINED)
    val authenticationState: StateFlow<AuthenticationState> = _authenticationState

    fun validateUser(firebaseUser: FirebaseUser) {
        _loading.value = true
        viewModelScope.launch {
            authenticationUseCases.authenticateUserUseCase(firebaseUser)
                .addOnSuccessListener { document ->
                    session.run {
                        saveField(PREF_BUSINESS_CNPJ, document.getString(CNPJ_FIELD).toString())
                        saveField(PREF_USER_NAME, document.getString(USERNAME_FIELD).toString())
                        saveField(PREF_USER_CPF, document.getString(USERCPF_FIELD).toString())
                    }

                    _authenticationState.value = AuthenticationState.AUTHENTICATED
                    _loading.value = false
                }
                .addOnFailureListener {
                    _authenticationState.value = AuthenticationState.INVALID_AUTHENTICATION
                    _loading.value = false
                }
        }
    }
}


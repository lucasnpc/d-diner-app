package com.example.ddinerapp.featureAuthentication.presentation.signUp

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.ddinerapp.common.util.*
import com.example.ddinerapp.featureAuthentication.domain.useCases.AuthenticationUseCases
import com.google.firebase.auth.FirebaseUser
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SignUpViewModel @Inject constructor(
    private val storeManager: DataStoreManager,
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
                    storeManager.run {
                        setBusinessCnpj(document.getString(CNPJ_FIELD).toString())
                        setUserInstance(
                            document.getString(USERNAME_FIELD).toString(), document.getString(
                                USERCPF_FIELD
                            ).toString()
                        )
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


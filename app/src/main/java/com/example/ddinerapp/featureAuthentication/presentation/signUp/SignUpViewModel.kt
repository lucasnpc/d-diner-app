package com.example.ddinerapp.featureAuthentication.presentation.signUp

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.ddinerapp.common.util.AuthenticationState
import com.example.ddinerapp.common.util.CNPJ_FIELD
import com.example.ddinerapp.common.util.DataStoreManager
import com.example.ddinerapp.common.util.ROLE_FIELD
import com.example.ddinerapp.featureMain.domain.repository.MainRepository
import com.example.ddinerapp.featureMain.domain.useCases.MainUseCases
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SignUpViewModel @Inject constructor(
    private val repository: MainRepository,
    private val storeManager: DataStoreManager,
    private val mainUseCases: MainUseCases
) : ViewModel() {

    private val _loading = MutableStateFlow(false)
    val loading: StateFlow<Boolean> = _loading

    private val _authenticationState = MutableStateFlow(AuthenticationState.UNDEFINED)
    val authenticationState: StateFlow<AuthenticationState> = _authenticationState

    fun validateUser(firebaseUser: FirebaseUser) {
        _loading.value = true
        viewModelScope.launch {
            mainUseCases.authenticateUserUseCase(firebaseUser)
                .addOnSuccessListener { document ->
                    storeManager.run {
                        setUserRole(document.getString(ROLE_FIELD).toString())
                        setBusinessCnpj(document.getString(CNPJ_FIELD).toString())
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


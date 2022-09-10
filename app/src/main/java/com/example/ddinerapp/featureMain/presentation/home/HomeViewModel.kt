package com.example.ddinerapp.featureMain.presentation.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.ddinerapp.common.util.DataStoreManager
import com.example.ddinerapp.featureMain.domain.model.Desk
import com.example.ddinerapp.featureMain.domain.useCases.MainUseCases
import com.google.firebase.firestore.ktx.toObject
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val store: DataStoreManager,
    private val mainUseCases: MainUseCases
) : ViewModel() {

    private val _desks = MutableStateFlow(arrayListOf<Desk>())
    val desks: StateFlow<List<Desk>> = _desks

    fun getUserRole() = viewModelScope.launch {
        store.userRole.first()
    }

    fun clearPreferences() = store.clearDataStore()

    fun getDesks() {
        viewModelScope.launch {
            mainUseCases.getDesksUseCase(store.businessCnpj.first())
                .addOnSuccessListener { query ->
                    val array = arrayListOf<Desk>()
                    query.documents.forEach { document ->
                        document.toObject<Desk>()?.let { array.add(it) }
                    }
                    _desks.value = array
                }.addOnFailureListener {
                    println(it.message)
                }
        }
    }
}
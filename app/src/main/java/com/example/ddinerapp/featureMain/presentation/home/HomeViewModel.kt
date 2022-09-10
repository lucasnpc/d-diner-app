package com.example.ddinerapp.featureMain.presentation.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.ddinerapp.common.util.DataStoreManager
import com.example.ddinerapp.featureMain.domain.model.Desk
import com.example.ddinerapp.featureMain.domain.model.MenuItem
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

    private val _items = MutableStateFlow(arrayListOf<MenuItem>())
    val items: StateFlow<List<MenuItem>> = _items

    init {
        getDesks()
        getMenuItems()
    }

    fun getUserRole() = viewModelScope.launch {
        store.userRole.first()
    }

    fun clearPreferences() = store.clearDataStore()

    private fun getDesks() {
        viewModelScope.launch {
            mainUseCases.getDesksUseCase(store.businessCnpj.first())
                .addOnSuccessListener { query ->
                    val array = arrayListOf<Desk>()
                    query.documents.map { doc ->
                        doc.toObject<Desk>()?.let { array.add(it) }
                    }

                    _desks.value = array
                }.addOnFailureListener {
                    println(it.message)
                }
        }
    }

    private fun getMenuItems() {
        viewModelScope.launch {
            mainUseCases.getMenuItemsUseCase(store.businessCnpj.first())
                .addOnSuccessListener { query ->
                    val array = arrayListOf<MenuItem>()
                    query.documents.map { doc ->
                        doc.toObject<MenuItem>()?.let { array.add(it) }
                    }

                    _items.value = array
                }
                .addOnFailureListener {
                    println(it.message)
                }
        }
    }
}
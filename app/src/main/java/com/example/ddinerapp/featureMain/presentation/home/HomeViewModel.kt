package com.example.ddinerapp.featureMain.presentation.home

import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.ddinerapp.common.util.DataStoreManager
import com.example.ddinerapp.featureMain.domain.model.Desk
import com.example.ddinerapp.featureMain.domain.model.MenuItem
import com.example.ddinerapp.featureMain.domain.useCases.MainUseCases
import com.google.firebase.firestore.DocumentChange
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val store: DataStoreManager,
    private val mainUseCases: MainUseCases
) : ViewModel() {

    private val _desks = mutableStateListOf<Desk>()
    val desks: List<Desk> = _desks

    private val _items = mutableStateListOf<MenuItem>()
    val items: List<MenuItem> = _items

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
                .addSnapshotListener { snapshot, exception ->

                    if (exception != null) {
                        println(exception.message)
                        return@addSnapshotListener
                    }

                    snapshot?.documentChanges?.map { doc ->
                        when (doc.type) {
                            DocumentChange.Type.ADDED -> doc.document.let {
                                _desks.add(
                                    Desk(
                                        id = it.id,
                                        description = it["description"] as String,
                                        isOccupied = it["isOccupied"] as Boolean
                                    )
                                )
                            }
                            DocumentChange.Type.MODIFIED -> doc.document.let {
                                _desks.run {
                                    val index =
                                        find { desk -> it["description"] == desk.description }
                                    remove(index)
                                    add(
                                        Desk(
                                            description = it["description"].toString(),
                                            isOccupied = it["isOccupied"] as Boolean
                                        )
                                    )
                                }
                            }
                            else -> Unit
                        }
                    }
                }
        }
    }

    private fun getMenuItems() {
        viewModelScope.launch {
            mainUseCases.getMenuItemsUseCase(store.businessCnpj.first())
                .addSnapshotListener { snapshot, exception ->
                    if (exception != null) {
                        println(exception.message)
                        return@addSnapshotListener
                    }

                    snapshot?.documentChanges?.map { doc ->
                        when (doc.type) {
                            DocumentChange.Type.ADDED -> doc.document.let {
                                _items.add(
                                    MenuItem(
                                        id = it.id,
                                        price = it["price"].toString().toDouble(),
                                        description = it["description"].toString(),
                                        category = it["category"].toString()
                                    )
                                )
                            }
                            else -> Unit
                        }
                    }
                }
        }
    }

    fun setOccupiedDesk(desk: Desk) {
        viewModelScope.launch {
            mainUseCases.setOccupiedDeskUseCase(desk, store.businessCnpj.first())
        }
    }

    fun createOrderItem() {
        viewModelScope.launch { }
    }
}
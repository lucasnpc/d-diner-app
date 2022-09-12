package com.example.ddinerapp.featureMain.presentation.orderingItems

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.ddinerapp.common.util.DataStoreManager
import com.example.ddinerapp.featureMain.domain.model.MenuItem
import com.example.ddinerapp.featureMain.domain.useCases.MainUseCases
import com.google.firebase.firestore.DocumentChange
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MenuItemViewModel @Inject constructor(
    private val storeManager: DataStoreManager,
    private val mainUseCases: MainUseCases
) : ViewModel() {

    private val _loading = mutableStateOf(false)
    val loading: State<Boolean> = _loading

    private val _items = mutableStateListOf<MenuItem>()
    val items: List<MenuItem> = _items

    init {
        getMenuItems()
    }

    private fun getMenuItems() {
        _loading.value = true
        viewModelScope.launch {
            mainUseCases.getMenuItemsUseCase(storeManager.businessCnpj.first())
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
                    _loading.value = false
                }
        }
    }
}
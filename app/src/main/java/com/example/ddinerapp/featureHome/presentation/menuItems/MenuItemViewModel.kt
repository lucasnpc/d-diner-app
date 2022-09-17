package com.example.ddinerapp.featureHome.presentation.menuItems

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.ddinerapp.common.util.DataStoreManager
import com.example.ddinerapp.featureHome.domain.model.MenuItem
import com.example.ddinerapp.featureHome.domain.useCases.HomeUseCases
import com.google.firebase.firestore.DocumentChange
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MenuItemViewModel @Inject constructor(
    private val storeManager: DataStoreManager,
    private val homeUseCases: HomeUseCases
) : ViewModel() {

    private val _loading = mutableStateOf(false)
    val loading: State<Boolean> = _loading

    private val _items = mutableStateListOf<MenuItem>()
    val items: List<MenuItem> = _items

    init {
        getMenuItems()
        getCurrentOrder()
    }

    private fun getMenuItems() {
        _loading.value = true
        viewModelScope.launch {
            homeUseCases.getMenuItemsUseCase(storeManager.businessCnpj.first())
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

    private fun getCurrentOrder() {
        viewModelScope.launch {
            storeManager.run {
                homeUseCases.getDeskOrders(businessCnpj.first(), deskId.first())
                    .addSnapshotListener { snapshot, exception ->
                        if (exception != null) {
                            println(exception.message)
                            return@addSnapshotListener
                        }

                        storeManager.setCurrentOrder(snapshot?.documents?.first()?.id.toString())
                    }
            }
        }
    }

    fun placeOrder(placedOrders: Map<String, Double>, observations: String) {
        viewModelScope.launch {
            storeManager.run {
                homeUseCases.placeOrdersUseCase(
                    businessCnpj.first(),
                    deskId.first(),
                    orderId.first(),
                    placedOrders,
                    observations
                )
            }
        }
    }
}
package com.example.ddinerapp.featureHome.presentation.menuItems

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.ddinerapp.common.data.session.DDinerSession
import com.example.ddinerapp.common.data.session.SessionPreferencesKeys.PREF_BUSINESS_CNPJ
import com.example.ddinerapp.common.data.session.SessionPreferencesKeys.PREF_CURRENT_ORDER_ID
import com.example.ddinerapp.common.data.session.SessionPreferencesKeys.PREF_SELECTED_DESK_ID
import com.example.ddinerapp.featureHome.domain.model.ItemProducts
import com.example.ddinerapp.featureHome.domain.model.MenuItem
import com.example.ddinerapp.featureHome.domain.useCases.HomeUseCases
import com.google.firebase.firestore.DocumentChange
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch

@HiltViewModel
class MenuItemViewModel @Inject constructor(
    private val session: DDinerSession,
    private val homeUseCases: HomeUseCases
) : ViewModel() {

    private val _loading = mutableStateOf(false)
    val loading: State<Boolean> = _loading

    private val _items = mutableStateListOf<MenuItem>()
    val items: List<MenuItem> = _items

    private val _itemProducts = mutableStateListOf<ItemProducts>()
    val itemProducts: List<ItemProducts> = _itemProducts

    init {
        getMenuItems()
        getCurrentOrder()
    }

    private fun getMenuItems() {
        _loading.value = true
        viewModelScope.launch {
            homeUseCases.getMenuItemsUseCase.getItems(session.getField(PREF_BUSINESS_CNPJ).first())
                .addSnapshotListener { snapshot, exception ->
                    if (exception != null) {
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
        session.run {
            viewModelScope.launch {
                homeUseCases.getCurrentDeskOrder(
                    getField(PREF_BUSINESS_CNPJ).first(),
                    getField(PREF_SELECTED_DESK_ID).first()
                )
                    .addOnSuccessListener { query ->
                        query?.documents?.let {
                            if (it.isNotEmpty())
                                session.saveField(PREF_CURRENT_ORDER_ID, it.first().id)
                        }
                    }.addOnFailureListener {
                    }
            }
        }
    }

    fun placeOrder(placedOrders: Map<String, Double>, observations: String) {
        viewModelScope.launch {
            session.run {
                homeUseCases.placeOrdersUseCase(
                    getField(PREF_BUSINESS_CNPJ).first(),
                    getField(PREF_SELECTED_DESK_ID).first(),
                    getField(PREF_CURRENT_ORDER_ID).first(),
                    placedOrders,
                    observations
                )
            }
        }
    }

    fun getItemProducts(id: String) {
        _itemProducts.clear()
        viewModelScope.launch {
            session.run {
                homeUseCases.getItemProducts(getField(PREF_BUSINESS_CNPJ).first(), id)
                    .addOnSuccessListener { snapshot ->
                        snapshot.documents.forEach {
                            _itemProducts.add(
                                ItemProducts(
                                    description = it["description"].toString(),
                                    quantity = it["quantity"].toString().toDouble(),
                                )
                            )
                        }
                    }
            }
        }
    }
}
package com.example.ddinerapp.featureMain.presentation.cart

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.ddinerapp.common.util.DataStoreManager
import com.example.ddinerapp.common.util.OrderedItemsKeys
import com.example.ddinerapp.featureMain.domain.model.OrderedItems
import com.example.ddinerapp.featureMain.domain.useCases.MainUseCases
import com.google.firebase.firestore.DocumentChange
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CartViewModel @Inject constructor(
    private val mainUseCases: MainUseCases,
    private val storeManager: DataStoreManager
) : ViewModel() {

    private val _loading = mutableStateOf(false)
    val loading: State<Boolean> = _loading

    private val _orderedItems = mutableListOf<OrderedItems>()
    val orderedItems: List<OrderedItems> = _orderedItems

    init {
        getOrderedItems()
    }

    private fun getOrderedItems() {
        _loading.value = true
        viewModelScope.launch {
            storeManager.run {
                mainUseCases.getOrderedItemsUseCase(
                    businessCnpj.first(),
                    deskId.first(),
                    orderId.first()
                ).addSnapshotListener { snapshot, exception ->
                    if (exception != null) {
                        println(exception.message)
                        return@addSnapshotListener
                    }

                    snapshot?.documentChanges?.map { doc ->
                        when (doc.type) {
                            DocumentChange.Type.ADDED -> {
                                doc.document.let {
                                    _orderedItems.add(
                                        OrderedItems(
                                            id = it.id,
                                            itemId = it[OrderedItemsKeys.ITEM_ID].toString(),
                                            observations = it[OrderedItemsKeys.OBSERVATIONS].toString(),
                                            quantity = it[OrderedItemsKeys.QUANTITY].toString()
                                                .toDouble(),
                                            status = it[OrderedItemsKeys.STATUS].toString()
                                        )
                                    )
                                }
                            }
                            else -> Unit
                        }
                    }
                    _loading.value = false
                }
            }
        }
    }

}
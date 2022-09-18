package com.example.ddinerapp.featureHome.presentation.cart

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.ddinerapp.common.util.DataStoreManager
import com.example.ddinerapp.common.util.OrderedItemsKeys
import com.example.ddinerapp.featureHome.domain.model.PlacedItems
import com.example.ddinerapp.featureHome.domain.useCases.HomeUseCases
import com.google.firebase.firestore.DocumentChange
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CartViewModel @Inject constructor(
    private val homeUseCases: HomeUseCases,
    private val storeManager: DataStoreManager
) : ViewModel() {

    private val _loading = mutableStateOf(false)
    val loading: State<Boolean> = _loading

    private val _placedItems = mutableStateOf(PlacedItems())
    val placedItems: State<PlacedItems> = _placedItems

    init {
        getPlacedItems()
    }

    private fun getPlacedItems() {
        _loading.value = true
        viewModelScope.launch {
            storeManager.run {
                homeUseCases.getOrderedItemsUseCase(
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
                                    _placedItems.value = PlacedItems(
                                        id = it.id,
                                        placedItems = it[OrderedItemsKeys.PLACED_ITEMS] as Map<String, Double>,
                                        observations = it[OrderedItemsKeys.OBSERVATIONS].toString(),
                                        status = it[OrderedItemsKeys.STATUS].toString()
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
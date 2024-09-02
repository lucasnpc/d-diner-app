package com.example.ddinerapp.featureHome.presentation.cart

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.ddinerapp.common.data.session.DDinerSession
import com.example.ddinerapp.common.data.session.SessionPreferencesKeys.PREF_BUSINESS_CNPJ
import com.example.ddinerapp.common.data.session.SessionPreferencesKeys.PREF_CURRENT_ORDER_ID
import com.example.ddinerapp.common.data.session.SessionPreferencesKeys.PREF_SELECTED_DESK_ID
import com.example.ddinerapp.common.util.OrderedItemsKeys
import com.example.ddinerapp.featureHome.domain.model.OrderedItems
import com.example.ddinerapp.featureHome.domain.useCases.HomeUseCases
import com.google.firebase.firestore.DocumentChange
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch

@HiltViewModel
class CartViewModel @Inject constructor(
    private val homeUseCases: HomeUseCases,
    private val session: DDinerSession
) : ViewModel() {

    var loading by mutableStateOf(false)
        private set

    private val _orderedItems = mutableListOf<OrderedItems>()
    val orderedItems: List<OrderedItems> = _orderedItems

    init {
        getPlacedItems()
    }

    private fun getPlacedItems() {
        loading = true
        viewModelScope.launch {
            session.run {
                homeUseCases.getOrderedItemsUseCase(
                    getField(PREF_BUSINESS_CNPJ).first(),
                    getField(PREF_SELECTED_DESK_ID).first(),
                    getField(PREF_CURRENT_ORDER_ID).first()
                ).addSnapshotListener { snapshot, exception ->
                    if (exception != null) {
                        return@addSnapshotListener
                    }

                    snapshot?.documentChanges?.map { doc ->
                        when (doc.type) {
                            DocumentChange.Type.ADDED -> {
                                doc.document.let {
                                    _orderedItems.add(
                                        OrderedItems(
                                            id = it.id,
                                            placedItems = it[OrderedItemsKeys.PLACED_ITEMS] as Map<String, Double>,
                                            observations = it[OrderedItemsKeys.OBSERVATIONS].toString(),
                                            status = it[OrderedItemsKeys.STATUS].toString()
                                        )
                                    )
                                }
                            }

                            else -> Unit
                        }
                    }
                    loading = false
                }
            }
        }
    }

    fun registerGain(selectedOption: String, total: Double) {
        viewModelScope.launch {
            homeUseCases.registerGainUseCase(
                session.getField(PREF_BUSINESS_CNPJ).first(),
                selectedOption,
                total
            )
        }
    }
}
package com.example.ddinerapp.featureMain.presentation.orders

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.ddinerapp.common.util.DataStoreManager
import com.example.ddinerapp.common.util.OrderKeys
import com.example.ddinerapp.featureMain.domain.model.Order
import com.example.ddinerapp.featureMain.domain.useCases.MainUseCases
import com.google.firebase.firestore.DocumentChange
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class OrdersViewModel @Inject constructor(
    private val storeManager: DataStoreManager,
    private val mainUseCases: MainUseCases
) : ViewModel() {

    private val _loading = mutableStateOf(false)
    val loading: State<Boolean> = _loading

    private val _orders = mutableStateListOf<Order>()
    val orders: List<Order> = _orders

    init {
        getDeskOrders()
    }

    private fun getDeskOrders() {
        _loading.value = true
        viewModelScope.launch {
            storeManager.run {
                mainUseCases.getDeskOrders(businessCnpj.first(), deskId.first())
                    .addSnapshotListener { snapshot, exception ->
                        if (exception != null) {
                            println(exception.message)
                            return@addSnapshotListener
                        }

                        snapshot?.documentChanges?.map { doc ->
                            when (doc.type) {
                                DocumentChange.Type.ADDED -> doc.document.let {
                                    _orders.add(
                                        Order(
                                            id = it.id,
                                            concluded = it[OrderKeys.CONCLUDED] as Boolean,
                                            startDate = it[OrderKeys.START_DATE].toString(),
                                            endDate = it[OrderKeys.END_DATE].toString()
                                        )
                                    )
                                }
                                DocumentChange.Type.MODIFIED -> doc.document.let {
                                    _orders.run {
                                        val index =
                                            find { order -> it.id == order.id }
                                        remove(index)
                                        add(
                                            Order(
                                                id = it.id,
                                                concluded = it[OrderKeys.CONCLUDED] as Boolean,
                                                startDate = it[OrderKeys.START_DATE].toString(),
                                                endDate = it[OrderKeys.END_DATE].toString()
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
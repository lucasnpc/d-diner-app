package com.example.ddinerapp.featureHome.presentation.orders

import android.content.Context
import android.graphics.pdf.PdfDocument
import android.os.Environment
import android.widget.Toast
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.ddinerapp.R
import com.example.ddinerapp.common.util.DataStoreManager
import com.example.ddinerapp.common.util.OrderKeys
import com.example.ddinerapp.featureHome.domain.model.Order
import com.example.ddinerapp.featureHome.domain.useCases.HomeUseCases
import com.google.firebase.firestore.DocumentChange
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.io.File
import java.io.FileOutputStream
import java.io.IOException
import javax.inject.Inject

@HiltViewModel
class OrdersViewModel @Inject constructor(
    private val storeManager: DataStoreManager,
    private val homeUseCases: HomeUseCases
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
                homeUseCases.getConcludedDeskOrders(businessCnpj.first(), deskId.first())
                    .addSnapshotListener { snapshot, exception ->
                        if (exception != null) {
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
                                            startHour = it[OrderKeys.START_HOUR].toString(),
                                            endDate = it[OrderKeys.END_DATE].toString(),
                                            endHour = it[OrderKeys.END_HOUR].toString()
                                        )
                                    )
                                }
                                DocumentChange.Type.MODIFIED -> doc.document.let {
                                    _orders.run {
                                        val find = find { order -> it.id == order.id }
                                        set(
                                            indexOf(find), Order(
                                                id = it.id,
                                                concluded = it[OrderKeys.CONCLUDED] as Boolean,
                                                startDate = it[OrderKeys.START_DATE].toString(),
                                                startHour = it[OrderKeys.START_HOUR].toString(),
                                                endDate = it[OrderKeys.END_DATE].toString(),
                                                endHour = it[OrderKeys.END_HOUR].toString()
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

    fun concludeOrder(time: Long) {
        viewModelScope.launch {
            storeManager.run {
                homeUseCases.concludeOrderUseCase(
                    businessCnpj.first(),
                    deskId.first(),
                    orderId.first(),
                    time
                )
            }
        }
    }

    fun writeDoc(doc: PdfDocument, context: Context) {
        _loading.value = true
        viewModelScope.launch {
            try {
                withContext(Dispatchers.IO) {
                    doc.writeTo(
                        FileOutputStream(
                            File(
                                Environment.getExternalStorageDirectory().path,
                                context.getString(
                                    R.string.documents_path,
                                    storeManager.orderId.first()
                                )
                            )
                        )
                    )
                }
                Toast.makeText(
                    context,
                    context.getString(R.string.voucher_saved),
                    Toast.LENGTH_SHORT
                ).show()
                _loading.value = false
            } catch (e: IOException) {
                e.printStackTrace()
            }
            doc.close()
        }
    }
}
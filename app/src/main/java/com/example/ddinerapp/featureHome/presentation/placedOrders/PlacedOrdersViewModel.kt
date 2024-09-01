package com.example.ddinerapp.featureHome.presentation.placedOrders

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
import com.example.ddinerapp.common.data.request.ApiResult
import com.example.ddinerapp.common.data.session.DDinerSession
import com.example.ddinerapp.common.data.session.SessionPreferencesKeys.PREF_BUSINESS_CNPJ
import com.example.ddinerapp.common.data.session.SessionPreferencesKeys.PREF_CURRENT_ORDER_ID
import com.example.ddinerapp.common.data.session.SessionPreferencesKeys.PREF_SELECTED_DESK_ID
import com.example.ddinerapp.featureHome.domain.PlacedOrdersUseCases
import com.example.ddinerapp.featureHome.domain.model.Order
import dagger.hilt.android.lifecycle.HiltViewModel
import java.io.File
import java.io.FileNotFoundException
import java.io.FileOutputStream
import java.io.IOException
import javax.inject.Inject
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

@HiltViewModel
class PlacedOrdersViewModel @Inject constructor(
    private val session: DDinerSession,
    private val placedOrdersUseCases: PlacedOrdersUseCases
) : ViewModel() {

    private val _loading = mutableStateOf(false)
    val loading: State<Boolean> = _loading

    private val _orders = mutableStateListOf<Order>()
    val orders: List<Order> = _orders

    init {
        getDeskOrders()
    }

    private fun getDeskOrders() {
        viewModelScope.launch {
            session.run {
                placedOrdersUseCases.deskCompletedOrdersUseCase.getCompletedOrders(
                    cnpj = getField(PREF_BUSINESS_CNPJ).first(),
                    deskId = getField(PREF_SELECTED_DESK_ID).first()
                ).collect { result ->
                    when (result) {
                        is ApiResult.Success -> {
                            result.data.run {
                                _orders.find { order -> id == order.id }?.also { findOrder ->
                                    _orders[_orders.indexOf(findOrder)] = Order(
                                        id = id,
                                        concluded = concluded,
                                        startDate = startDate,
                                        startHour = startHour,
                                        endDate = endDate,
                                        endHour = endHour
                                    )
                                } ?: _orders.add(
                                    Order(
                                        id = id,
                                        concluded = concluded,
                                        startDate = startDate,
                                        startHour = startHour,
                                        endDate = endDate,
                                        endHour = endHour
                                    )
                                )
                            }
                        }

                        is ApiResult.Error -> {}
                    }
                }
            }
        }
    }

    fun completeOrderAtTime(time: Long) {
        viewModelScope.launch {
            session.run {
                placedOrdersUseCases.completeOrderUseCase.completeOrder(
                    getField(PREF_BUSINESS_CNPJ).first(),
                    getField(PREF_SELECTED_DESK_ID).first(),
                    getField(PREF_CURRENT_ORDER_ID).first(),
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
                                    session.getField(PREF_CURRENT_ORDER_ID).first()
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
            } catch (e: FileNotFoundException) {
                e.printStackTrace()
            } catch (e: IOException) {
                e.printStackTrace()
            }
            doc.close()
        }
    }
}
package com.example.ddinerapp.featureHome.data.repositories

import com.example.ddinerapp.common.data.request.ApiResult
import com.example.ddinerapp.common.data.request.safeRequest
import com.example.ddinerapp.common.util.BUSINESS_COLLECTION
import com.example.ddinerapp.common.util.DESKS_COLLECTION
import com.example.ddinerapp.common.util.ORDERS_COLLECTION
import com.example.ddinerapp.common.util.OrderKeys
import com.example.ddinerapp.featureHome.domain.model.Order
import com.example.ddinerapp.featureHome.domain.placedOrdersUseCases.DeskCompletedOrdersUseCase
import com.google.firebase.firestore.DocumentChange
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.channels.ProducerScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow

class DeskCompletedOrdersRepository(private val db: FirebaseFirestore) :
    DeskCompletedOrdersUseCase {

    override fun getCompletedOrders(cnpj: String, deskId: String): Flow<ApiResult<Order>> =
        callbackFlow {
            db.collection(BUSINESS_COLLECTION).document(cnpj).collection(DESKS_COLLECTION)
                .document(deskId).collection(ORDERS_COLLECTION)
                .whereEqualTo(OrderKeys.CONCLUDED, true)
                .limit(15).addSnapshotListener { snapshot, exception ->
                    if (exception != null) {
                        trySend(ApiResult.Error(exception))
                        return@addSnapshotListener
                    }
                    snapshot?.documentChanges?.map { documentChange ->
                        when (documentChange.type) {
                            DocumentChange.Type.ADDED -> {
                                emitOrder(documentChange)
                            }

                            DocumentChange.Type.MODIFIED -> {
                                emitOrder(documentChange)
                            }

                            else -> Unit
                        }
                    }
                }
        }

    private fun ProducerScope<ApiResult<Order>>.emitOrder(
        documentChange: DocumentChange
    ) = safeRequest {
        documentChange.document.run {
            trySend(
                ApiResult.Success(
                    Order(
                        id = id,
                        concluded = this[OrderKeys.CONCLUDED] as Boolean,
                        startDate = this[OrderKeys.START_DATE].toString(),
                        startHour = this[OrderKeys.START_HOUR].toString(),
                        endDate = this[OrderKeys.END_DATE].toString(),
                        endHour = this[OrderKeys.END_HOUR].toString()
                    )
                )
            )
        }
    }
}
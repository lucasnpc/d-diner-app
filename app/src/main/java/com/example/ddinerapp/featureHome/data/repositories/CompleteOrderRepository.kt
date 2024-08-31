package com.example.ddinerapp.featureHome.data.repositories

import com.example.ddinerapp.common.util.BUSINESS_COLLECTION
import com.example.ddinerapp.common.util.DESKS_COLLECTION
import com.example.ddinerapp.common.util.ORDERS_COLLECTION
import com.example.ddinerapp.common.util.toDateFormat
import com.example.ddinerapp.common.util.toHourFormat
import com.example.ddinerapp.featureHome.domain.placedOrdersUseCases.CompleteOrderUseCase
import com.google.android.gms.tasks.Task
import com.google.firebase.firestore.FirebaseFirestore

class CompleteOrderRepository(private val db: FirebaseFirestore) : CompleteOrderUseCase {

    override fun completeOrder(
        cnpj: String,
        deskId: String,
        orderId: String,
        time: Long
    ): Task<Void> {
        return db.collection(BUSINESS_COLLECTION).document(cnpj).collection(DESKS_COLLECTION)
            .document(deskId).collection(ORDERS_COLLECTION).document(orderId).update(
                "concluded",
                true,
                "endDate",
                time.toDateFormat(),
                "endHour",
                time.toHourFormat()
            )
    }
}
package com.example.ddinerapp.featureHome.domain.useCases

import com.example.ddinerapp.common.util.*
import com.google.android.gms.tasks.Task
import com.google.firebase.firestore.FirebaseFirestore

class ConcludeOrderUseCase(private val db: FirebaseFirestore) {

    operator fun invoke(cnpj: String, deskId: String, orderId: String): Task<Void> {
        return db.collection(BUSINESS_COLLECTION).document(cnpj).collection(DESKS_COLLECTION)
            .document(deskId).collection(ORDERS_COLLECTION).document(orderId).update(
                "concluded",
                true,
                "endDate",
                System.currentTimeMillis().toDateFormat(),
                "endHour",
                System.currentTimeMillis().toHourFormat()
            )
    }
}
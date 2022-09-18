package com.example.ddinerapp.featureHome.domain.useCases

import com.example.ddinerapp.common.util.BUSINESS_COLLECTION
import com.example.ddinerapp.common.util.DESKS_COLLECTION
import com.example.ddinerapp.common.util.ORDERS_COLLECTION
import com.google.android.gms.tasks.Task
import com.google.firebase.firestore.FirebaseFirestore
import java.text.SimpleDateFormat
import java.util.*

class ConcludeOrderUseCase(private val db: FirebaseFirestore) {

    operator fun invoke(cnpj: String, deskId: String, orderId: String): Task<Void> {
        val simpleDateFormat =
            SimpleDateFormat("dd 'de' MMMM 'de' yyyy HH:mm:ss", Locale.getDefault())

        return db.collection(BUSINESS_COLLECTION).document(cnpj).collection(DESKS_COLLECTION)
            .document(deskId).collection(ORDERS_COLLECTION).document(orderId).update(
                "concluded",
                true,
                "endDate",
                simpleDateFormat.format(System.currentTimeMillis())
            )
    }
}
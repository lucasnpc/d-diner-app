package com.example.ddinerapp.featureMain.domain.useCases

import com.example.ddinerapp.common.util.*
import com.example.ddinerapp.featureHome.domain.model.Order
import com.google.android.gms.tasks.Task
import com.google.firebase.firestore.DocumentReference
import com.google.firebase.firestore.FirebaseFirestore

class AddOrderUseCase(private val db: FirebaseFirestore) {

    operator fun invoke(id: String, cnpj: String): Task<DocumentReference> {
        val document =
            db.collection(BUSINESS_COLLECTION).document(cnpj).collection(DESKS_COLLECTION)
                .document(id)

        Order(
            startDate = System.currentTimeMillis().toDateFormat(),
            startHour = System.currentTimeMillis().toHourFormat()
        ).let {
            return document.collection(ORDERS_COLLECTION)
                .add(
                    hashMapOf(
                        OrderKeys.CONCLUDED to it.concluded,
                        OrderKeys.EMPLOYEE_CPF to it.employeeCpf,
                        OrderKeys.START_DATE to it.startDate,
                        OrderKeys.START_HOUR to it.startHour,
                        OrderKeys.END_DATE to it.endDate,
                        OrderKeys.END_HOUR to it.endHour
                    )
                )
        }
    }
}
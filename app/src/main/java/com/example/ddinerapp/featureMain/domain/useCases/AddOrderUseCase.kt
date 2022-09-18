package com.example.ddinerapp.featureMain.domain.useCases

import com.example.ddinerapp.common.util.BUSINESS_COLLECTION
import com.example.ddinerapp.common.util.DESKS_COLLECTION
import com.example.ddinerapp.common.util.ORDERS_COLLECTION
import com.example.ddinerapp.common.util.OrderKeys
import com.example.ddinerapp.featureHome.domain.model.Order
import com.google.android.gms.tasks.Task
import com.google.firebase.firestore.DocumentReference
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import java.text.SimpleDateFormat
import java.util.*

class AddOrderUseCase(private val db: FirebaseFirestore) {

    operator fun invoke(id: String, cnpj: String): Task<DocumentReference> {
        val document =
            db.collection(BUSINESS_COLLECTION).document(cnpj).collection(DESKS_COLLECTION)
                .document(id)

        val simpleDateFormat =
            SimpleDateFormat("dd 'de' MMMM 'de' yyyy HH:mm:ss", Locale.getDefault())

        Order(startDate = simpleDateFormat.format(System.currentTimeMillis())).let {
            return document.collection(ORDERS_COLLECTION)
                .add(
                    hashMapOf(
                        OrderKeys.CONCLUDED to it.concluded,
                        OrderKeys.EMPLOYEE_CPF to it.employeeCpf,
                        OrderKeys.START_DATE to it.startDate,
                        OrderKeys.END_DATE to it.endDate
                    )
                )
        }
    }
}
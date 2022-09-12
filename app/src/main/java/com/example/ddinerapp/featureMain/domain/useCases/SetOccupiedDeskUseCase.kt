package com.example.ddinerapp.featureMain.domain.useCases

import com.example.ddinerapp.common.util.BUSINESS_COLLECTION
import com.example.ddinerapp.common.util.DESKS_COLLECTION
import com.example.ddinerapp.common.util.ORDERS_COLLECTION
import com.example.ddinerapp.featureMain.domain.model.Desk
import com.example.ddinerapp.featureMain.domain.model.Order
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import java.text.SimpleDateFormat
import java.util.*

class SetOccupiedDeskUseCase {
    private val db = Firebase.firestore

    operator fun invoke(desk: Desk, cnpj: String) {
        val document =
            db.collection(BUSINESS_COLLECTION).document(cnpj).collection(DESKS_COLLECTION)
                .document(desk.id)
        document.update("isOccupied", true)

        val simpleDateFormat =
            SimpleDateFormat("dd 'de' MMMM 'de' yyyy HH:mm:ss", Locale.getDefault())

        Order(startDate = simpleDateFormat.format(System.currentTimeMillis())).let {
            document.collection(ORDERS_COLLECTION)
                .add(
                    hashMapOf(
                        "conclude" to it.concluded,
                        "employeeCpf" to it.employeeCpf,
                        "startDate" to it.startDate,
                        "endDate" to it.endDate
                    )
                )
        }

    }
}
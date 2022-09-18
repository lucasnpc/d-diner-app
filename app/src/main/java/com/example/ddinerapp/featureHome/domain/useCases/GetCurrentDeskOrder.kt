package com.example.ddinerapp.featureHome.domain.useCases

import com.example.ddinerapp.common.util.BUSINESS_COLLECTION
import com.example.ddinerapp.common.util.DESKS_COLLECTION
import com.example.ddinerapp.common.util.ORDERS_COLLECTION
import com.example.ddinerapp.common.util.OrderKeys
import com.google.android.gms.tasks.Task
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import com.google.firebase.firestore.QuerySnapshot

class GetCurrentDeskOrder(private val db: FirebaseFirestore) {
    operator fun invoke(cnpj: String, id: String): Task<QuerySnapshot> {
        return db.collection(BUSINESS_COLLECTION).document(cnpj).collection(DESKS_COLLECTION)
            .document(id).collection(ORDERS_COLLECTION).whereEqualTo(OrderKeys.CONCLUDED, false).get()
    }
}
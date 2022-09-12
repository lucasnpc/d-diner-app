package com.example.ddinerapp.featureMain.domain.useCases

import com.example.ddinerapp.common.util.BUSINESS_COLLECTION
import com.example.ddinerapp.common.util.DESKS_COLLECTION
import com.example.ddinerapp.common.util.ORDERS_COLLECTION
import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class GetDeskOrders {
    private val db = Firebase.firestore

    operator fun invoke(cnpj: String, id: String): CollectionReference {
        return db.collection(BUSINESS_COLLECTION).document(cnpj).collection(DESKS_COLLECTION)
            .document(id).collection(ORDERS_COLLECTION)
    }
}
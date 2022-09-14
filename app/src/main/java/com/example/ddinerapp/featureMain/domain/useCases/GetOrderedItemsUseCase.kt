package com.example.ddinerapp.featureMain.domain.useCases

import com.example.ddinerapp.common.util.*
import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class GetOrderedItemsUseCase {
    private val db = Firebase.firestore

    operator fun invoke(cnpj: String, deskId: String, orderId: String): CollectionReference {
       return db.collection(BUSINESS_COLLECTION).document(cnpj).collection(DESKS_COLLECTION)
            .document(deskId).collection(ORDERS_COLLECTION).document(orderId)
            .collection(ORDERED_ITEMS_COLLECTION)
    }
}
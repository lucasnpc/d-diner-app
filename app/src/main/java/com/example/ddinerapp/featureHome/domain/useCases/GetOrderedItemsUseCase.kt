package com.example.ddinerapp.featureHome.domain.useCases

import com.example.ddinerapp.common.util.BUSINESS_COLLECTION
import com.example.ddinerapp.common.util.DESKS_COLLECTION
import com.example.ddinerapp.common.util.ORDERED_ITEMS_COLLECTION
import com.example.ddinerapp.common.util.ORDERS_COLLECTION
import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.FirebaseFirestore

class GetOrderedItemsUseCase(private val db: FirebaseFirestore) {

    operator fun invoke(cnpj: String, deskId: String, orderId: String): CollectionReference {
       return db.collection(BUSINESS_COLLECTION).document(cnpj).collection(DESKS_COLLECTION)
            .document(deskId).collection(ORDERS_COLLECTION).document(orderId)
            .collection(ORDERED_ITEMS_COLLECTION)
    }
}
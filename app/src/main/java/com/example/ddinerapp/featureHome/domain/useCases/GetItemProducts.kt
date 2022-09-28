package com.example.ddinerapp.featureHome.domain.useCases

import com.example.ddinerapp.common.util.BUSINESS_COLLECTION
import com.example.ddinerapp.common.util.ITEMS_COLLECTION
import com.example.ddinerapp.common.util.ITEM_PRODUCTS_COLLECTION
import com.google.android.gms.tasks.Task
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.QuerySnapshot

class GetItemProducts(private val db: FirebaseFirestore) {
    operator fun invoke(cnpj: String, id: String): Task<QuerySnapshot> {
        return db.collection(BUSINESS_COLLECTION).document(cnpj).collection(ITEMS_COLLECTION)
            .document(id).collection(ITEM_PRODUCTS_COLLECTION).get()
    }
}
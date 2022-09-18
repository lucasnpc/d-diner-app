package com.example.ddinerapp.featureHome.domain.useCases

import com.example.ddinerapp.common.util.BUSINESS_COLLECTION
import com.example.ddinerapp.common.util.ITEMS_COLLECTION
import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.FirebaseFirestore

class GetMenuItemsUseCase(private val db: FirebaseFirestore) {

    operator fun invoke(cnpj: String): CollectionReference {
        return db.collection(BUSINESS_COLLECTION).document(cnpj).collection(ITEMS_COLLECTION)
    }
}
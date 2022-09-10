package com.example.ddinerapp.featureMain.domain.useCases

import com.example.ddinerapp.common.util.BUSINESS_COLLECTION
import com.example.ddinerapp.common.util.ITEMS_COLLECTION
import com.google.android.gms.tasks.Task
import com.google.firebase.firestore.QuerySnapshot
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class GetMenuItemsUseCase {
    private val db = Firebase.firestore

    operator fun invoke(cnpj: String): Task<QuerySnapshot> {
        return db.collection(BUSINESS_COLLECTION).document(cnpj).collection(ITEMS_COLLECTION).get()
    }
}
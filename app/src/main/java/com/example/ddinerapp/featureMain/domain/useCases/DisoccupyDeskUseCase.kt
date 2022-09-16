package com.example.ddinerapp.featureMain.domain.useCases

import com.example.ddinerapp.common.util.BUSINESS_COLLECTION
import com.example.ddinerapp.common.util.DESKS_COLLECTION
import com.google.android.gms.tasks.Task
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class DisoccupyDeskUseCase {
    private val db = Firebase.firestore

    operator fun invoke(cnpj: String, id: String): Task<Void> {
        return db.collection(BUSINESS_COLLECTION).document(cnpj).collection(DESKS_COLLECTION)
            .document(id)
            .update("isOccupied", false)
    }
}
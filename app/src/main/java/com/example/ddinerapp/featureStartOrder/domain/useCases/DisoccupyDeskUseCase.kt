package com.example.ddinerapp.featureStartOrder.domain.useCases

import com.example.ddinerapp.common.util.BUSINESS_COLLECTION
import com.example.ddinerapp.common.util.DESKS_COLLECTION
import com.google.android.gms.tasks.Task
import com.google.firebase.firestore.FirebaseFirestore

class DisoccupyDeskUseCase(private val db: FirebaseFirestore) {

    operator fun invoke(cnpj: String, id: String): Task<Void> {
        return db.collection(BUSINESS_COLLECTION).document(cnpj).collection(DESKS_COLLECTION)
            .document(id)
            .update("isOccupied", false)
    }
}
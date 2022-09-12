package com.example.ddinerapp.featureMain.domain.useCases

import com.example.ddinerapp.common.util.BUSINESS_COLLECTION
import com.example.ddinerapp.common.util.DESKS_COLLECTION
import com.example.ddinerapp.featureMain.domain.model.Desk
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class SetOccupiedDeskUseCase {
    private val db = Firebase.firestore

    operator fun invoke(desk: Desk, cnpj: String) {
        val document =
            db.collection(BUSINESS_COLLECTION).document(cnpj).collection(DESKS_COLLECTION)
                .document(desk.id)
        document.update("isOccupied", true)
    }
}
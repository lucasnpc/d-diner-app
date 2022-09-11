package com.example.ddinerapp.featureMain.domain.useCases

import com.example.ddinerapp.common.util.BUSINESS_COLLECTION
import com.example.ddinerapp.common.util.DESKS_COLLECTION
import com.example.ddinerapp.featureMain.domain.model.Desk
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class SetOccupiedDeskUseCase {
    private val db = Firebase.firestore

    operator fun invoke(desk: Desk, cnpj: String) {
        db.collection(BUSINESS_COLLECTION).document(cnpj).collection(DESKS_COLLECTION)
            .whereEqualTo("description", desk.description).addSnapshotListener { value, e ->
                if (e != null) {
                    println(e.message)
                    return@addSnapshotListener
                }
                value?.forEach {
                    it.reference.update("isOccupied", true)
                }
            }
    }
}
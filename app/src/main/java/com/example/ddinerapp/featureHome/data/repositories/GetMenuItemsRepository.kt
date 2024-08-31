package com.example.ddinerapp.featureHome.data.repositories

import com.example.ddinerapp.common.util.BUSINESS_COLLECTION
import com.example.ddinerapp.common.util.ITEMS_COLLECTION
import com.example.ddinerapp.featureHome.domain.useCases.GetMenuItemsUseCase
import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.FirebaseFirestore

class GetMenuItemsRepository(private val db: FirebaseFirestore) : GetMenuItemsUseCase {

    override fun getItems(cnpj: String): CollectionReference {
        return db.collection(BUSINESS_COLLECTION).document(cnpj).collection(ITEMS_COLLECTION)
    }
}
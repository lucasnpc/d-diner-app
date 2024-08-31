package com.example.ddinerapp.featureHome.domain.useCases

import com.google.firebase.firestore.CollectionReference

interface GetMenuItemsUseCase {
    fun getItems(cnpj: String): CollectionReference
}
package com.example.ddinerapp.featureMain.domain.useCases

import com.example.ddinerapp.common.util.*
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class PlaceOrdersUseCase {
    private val db = Firebase.firestore

    operator fun invoke(
        cnpj: String,
        deskId: String,
        orderId: String,
        placedOrders: Map<String, Double>,
        observations: String = ""
    ) {
        placedOrders.forEach {
            db.collection(BUSINESS_COLLECTION).document(cnpj).collection(DESKS_COLLECTION)
                .document(deskId).collection(ORDERS_COLLECTION).document(orderId)
                .collection(ORDERED_ITEMS_COLLECTION).add(
                    hashMapOf(
                        OrderedItemsKeys.ITEM_ID to it.key,
                        OrderedItemsKeys.OBSERVATIONS to observations,
                        OrderedItemsKeys.QUANTITY to it.value,
                        OrderedItemsKeys.STATUS to "enviado"
                    )
                )
        }

    }
}
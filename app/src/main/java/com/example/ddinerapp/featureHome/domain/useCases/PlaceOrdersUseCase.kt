package com.example.ddinerapp.featureHome.domain.useCases

import com.example.ddinerapp.common.util.*
import com.google.firebase.firestore.FirebaseFirestore

class PlaceOrdersUseCase(private val db: FirebaseFirestore) {

    operator fun invoke(
        cnpj: String,
        deskId: String,
        orderId: String,
        placedOrders: Map<String, Double>,
        observations: String = ""
    ) {
        db.collection(BUSINESS_COLLECTION).document(cnpj).collection(DESKS_COLLECTION)
            .document(deskId).collection(ORDERS_COLLECTION).document(orderId)
            .collection(ORDERED_ITEMS_COLLECTION).add(
                hashMapOf(
                    OrderedItemsKeys.PLACED_ITEMS to placedOrders,
                    OrderedItemsKeys.OBSERVATIONS to observations,
                    OrderedItemsKeys.STATUS to "enviado"
                )
            )

    }
}
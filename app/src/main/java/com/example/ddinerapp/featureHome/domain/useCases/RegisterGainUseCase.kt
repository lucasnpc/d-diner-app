package com.example.ddinerapp.featureHome.domain.useCases

import com.example.ddinerapp.common.util.BUSINESS_COLLECTION
import com.example.ddinerapp.common.util.GAINS_COLLECTION
import com.example.ddinerapp.common.util.GainKeys
import com.example.ddinerapp.common.util.toDateFormat
import com.google.android.gms.tasks.Task
import com.google.firebase.firestore.DocumentReference
import com.google.firebase.firestore.FirebaseFirestore

class RegisterGainUseCase(private val db: FirebaseFirestore) {
    operator fun invoke(cnpj: String, paymentWay: String, value: Double): Task<DocumentReference> {
        return db.collection(BUSINESS_COLLECTION).document(cnpj).collection(GAINS_COLLECTION).add(
            hashMapOf(
                GainKeys.ADDITIONAL_VALUE to 0.0,
                GainKeys.GAIN_DATE to System.currentTimeMillis().toDateFormat(),
                GainKeys.PAYMENT_WAY to paymentWay,
                GainKeys.VALUE to value
            )
        )
    }
}
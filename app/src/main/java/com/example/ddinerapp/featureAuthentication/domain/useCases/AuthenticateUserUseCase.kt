package com.example.ddinerapp.featureAuthentication.domain.useCases

import com.example.ddinerapp.common.util.USERS_COLLECTION
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class AuthenticateUserUseCase {

    private val db = Firebase.firestore

    operator fun invoke(user: FirebaseUser): Task<DocumentSnapshot> {
        return db.collection(USERS_COLLECTION).document(user.email.toString()).get()
    }
}
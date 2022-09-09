package com.example.ddinerapp.featureAuthentication

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.ddinerapp.databinding.ActivityAuthenticationBinding
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class AuthenticationActivity : AppCompatActivity() {

    private lateinit var binding: ActivityAuthenticationBinding
    val db = Firebase.firestore

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        db.collection("").add(Any()).addOnSuccessListener {

        }

        binding = ActivityAuthenticationBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }
}
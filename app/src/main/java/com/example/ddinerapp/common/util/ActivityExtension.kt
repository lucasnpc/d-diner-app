package com.example.ddinerapp.common.util

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import com.example.ddinerapp.featureAuthentication.AuthenticationActivity

fun AppCompatActivity.observeAuthState(clearStorage: (Boolean) -> Unit) {
    FirebaseUserLiveData().observe(this) {
        if (it == null) {
            startActivity(Intent(this, AuthenticationActivity::class.java))
            clearStorage(true)
            finish()
        }
    }
}
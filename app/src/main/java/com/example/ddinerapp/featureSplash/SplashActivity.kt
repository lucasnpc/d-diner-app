package com.example.ddinerapp.featureSplash

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.example.ddinerapp.R
import com.example.ddinerapp.common.util.FirebaseUserLiveData
import com.example.ddinerapp.databinding.ActivitySplashBinding
import com.example.ddinerapp.featureAuthentication.AuthenticationActivity
import com.example.ddinerapp.featureStartOrder.presentation.StartOrderActivity
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@SuppressLint("CustomSplashScreen")
class SplashActivity : AppCompatActivity() {

    private val binding: ActivitySplashBinding by lazy {
        ActivitySplashBinding.inflate(layoutInflater)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        lifecycleScope.launch {
            binding.motionLayout.setTransition(R.id.logo_d_transition)
            delay(600)
            binding.motionLayout.setTransition(R.id.restaurant_icon_transition)
            delay(1000)
            FirebaseUserLiveData().observe(this@SplashActivity) {
                it?.let {
                    startActivity(Intent(this@SplashActivity, StartOrderActivity::class.java))
                } ?: startActivity(Intent(this@SplashActivity, AuthenticationActivity::class.java))
                finish()
            }
        }
    }
}
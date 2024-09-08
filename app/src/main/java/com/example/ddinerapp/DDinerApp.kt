package com.example.ddinerapp

import android.app.Application
import com.datadog.android.Datadog
import com.datadog.android.DatadogSite
import com.datadog.android.core.configuration.Configuration
import com.datadog.android.privacy.TrackingConsent
import com.datadog.android.rum.Rum
import com.datadog.android.rum.RumConfiguration
import com.datadog.android.rum.tracking.ActivityViewTrackingStrategy
import com.google.firebase.remoteconfig.FirebaseRemoteConfig
import dagger.hilt.android.HiltAndroidApp


@HiltAndroidApp
class DDinerApp : Application() {
    override fun onCreate() {
        super.onCreate()

        FirebaseRemoteConfig.getInstance().also { instance ->
            instance.fetchAndActivate().addOnCompleteListener {
                if (it.isSuccessful) {
                    initDataDog()
                    if (instance.getBoolean("IS_DATADOG_RUM_AVAILABLE")) {
                        initPerformanceMonitoring()
                    }
                }
            }
        }
    }

    private fun initDataDog(
        clientToken: String = BuildConfig.DATADOG_CLIENT_TOKEN,
    ) {
        if (Datadog.isInitialized()) return
        val environmentName = "production"

        val configuration = Configuration.Builder(
            clientToken = clientToken,
            env = environmentName,
        )
            .useSite(DatadogSite.US5)
            .build()
        Datadog.initialize(this, configuration, TrackingConsent.GRANTED)
    }

    private fun initPerformanceMonitoring(applicationId: String = BuildConfig.DATADOG_APP_ID) {
        val rumConfiguration = RumConfiguration.Builder(applicationId)
            .trackUserInteractions()
            .trackLongTasks()
            .useViewTrackingStrategy(ActivityViewTrackingStrategy(trackExtras = true))
            .build()
        Rum.enable(rumConfiguration)
    }

}
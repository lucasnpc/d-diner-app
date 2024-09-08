package com.example.ddinerapp.common.data.logger

import com.datadog.android.log.Logger
import com.datadog.android.log.Logs
import com.datadog.android.log.LogsConfiguration


sealed interface DDinerLogger {
    fun recordCompletedOrder(id: String)
}

fun provideDatadogLogger(): DDinerLogger = DatadogLogger

internal data object DatadogLogger : DDinerLogger {
    private val logger by lazy {
        Logger.Builder()
            .setNetworkInfoEnabled(true)
            .setLogcatLogsEnabled(true)
            .setRemoteSampleRate(100f)
            .setBundleWithTraceEnabled(true)
            .setName("ddinerapp.datadoglogger")
            .build()
    }

    init {
        LogsConfiguration.Builder().build().also {
            Logs.enable(it)
        }
    }

    override fun recordCompletedOrder(id: String) {
        logger.i("Completed Order", attributes = mapOf("completedOrderId" to id))
    }
}
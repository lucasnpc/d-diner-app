package com.example.ddinerapp.common.util

import kotlinx.serialization.Serializable

@Serializable
data class Response<T>(
    val success: Boolean,
    val data: T
)

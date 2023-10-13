package com.example.ddinerapp.common.util

data class Response<T>(
    val success: Boolean,
    val data: T
)

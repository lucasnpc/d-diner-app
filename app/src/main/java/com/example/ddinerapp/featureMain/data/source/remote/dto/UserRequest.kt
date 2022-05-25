package com.example.ddinerapp.featureMain.data.source.remote.dto

import kotlinx.serialization.Serializable

@Serializable
data class UserRequest(
    val email: String,
    val password: String
)
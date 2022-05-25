package com.example.ddinerapp.featureMain.data.source.remote.dto

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import kotlinx.serialization.Serializable

@Parcelize
@Serializable
data class UserResponse(
    val email: String,
    val userType: String,
    val businessCnpj: String
) : Parcelable
package com.example.ddinerapp.featureMain.domain.repository

import com.example.ddinerapp.common.util.Response
import com.example.ddinerapp.featureMain.data.source.remote.dto.UserResponse

interface MainRepository {
    suspend fun authUsers(username: String, password: String): Response<UserResponse>?
}
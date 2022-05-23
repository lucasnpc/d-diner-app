package com.example.ddinerapp.featureMain.domain.remote

import com.example.ddinerapp.featureMain.data.source.remote.dto.UserResponse

interface MainService {

    suspend fun authenticateUser(username: String, password: String): UserResponse
}
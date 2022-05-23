package com.example.ddinerapp.featureMain.data.source.remote

import com.example.ddinerapp.featureMain.data.source.remote.dto.UserResponse
import com.example.ddinerapp.featureMain.domain.remote.MainService
import io.ktor.client.*

class MainServiceImpl(private val client: HttpClient) : MainService {
    override suspend fun authenticateUser(username: String, password: String): UserResponse {
        TODO("Not yet implemented")
    }
}
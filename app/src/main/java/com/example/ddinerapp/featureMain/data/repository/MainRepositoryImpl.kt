package com.example.ddinerapp.featureMain.data.repository

import com.example.ddinerapp.featureMain.data.source.remote.dto.UserRequest
import com.example.ddinerapp.featureMain.domain.remote.MainService
import com.example.ddinerapp.featureMain.domain.repository.MainRepository

class MainRepositoryImpl(private val service: MainService) : MainRepository {
    override suspend fun authUsers(username: String, password: String) {
        service.authenticateUser(UserRequest(username, password))
    }
}
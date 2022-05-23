package com.example.ddinerapp.featureMain.data.repository

import com.example.ddinerapp.featureMain.data.source.remote.dto.UserResponse
import com.example.ddinerapp.featureMain.domain.remote.MainService
import com.example.ddinerapp.featureMain.domain.repository.MainRepository

class MainRepositoryImpl(private val service: MainService) : MainRepository {
    override fun authUsers(username: String, password: String): UserResponse {
        TODO("Not yet implemented")
    }
}
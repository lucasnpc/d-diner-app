package com.example.ddinerapp.featureMain.domain.repository

interface MainRepository {
    suspend fun authUsers(username: String, password: String)
}
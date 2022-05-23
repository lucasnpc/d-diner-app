package com.example.ddinerapp.featureMain.domain.repository

import com.example.ddinerapp.featureMain.data.source.remote.dto.UserResponse

interface MainRepository {
    fun authUsers(username: String, password: String): UserResponse
}
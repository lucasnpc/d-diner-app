package com.example.ddinerapp.featureMain.domain.remote

import com.example.ddinerapp.common.util.Response
import com.example.ddinerapp.featureMain.data.source.remote.dto.UserRequest
import com.example.ddinerapp.featureMain.data.source.remote.dto.UserResponse

interface MainService {

    suspend fun authenticateUser(userRequest: UserRequest): Response<UserResponse>?
}
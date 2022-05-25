package com.example.ddinerapp.featureMain.data.source.remote

import com.example.ddinerapp.common.util.MainRoutes
import com.example.ddinerapp.common.util.Response
import com.example.ddinerapp.featureMain.data.source.remote.dto.UserRequest
import com.example.ddinerapp.featureMain.data.source.remote.dto.UserResponse
import com.example.ddinerapp.featureMain.domain.remote.MainService
import io.ktor.client.*
import io.ktor.client.features.*
import io.ktor.client.request.*
import io.ktor.http.*

class MainServiceImpl(private val client: HttpClient) : MainService {
    override suspend fun authenticateUser(userRequest: UserRequest): Response<UserResponse>? =
        try {
            client.post {
                url(MainRoutes.AUTH_USER)
                contentType(ContentType.Application.Json)
                body = userRequest
            }
        } catch (e: RedirectResponseException) {
            println("Error: ${e.response.status.description}")
            null
        } catch (e: ClientRequestException) {
            println("Error: ${e.response.status.description}")
            null
        } catch (e: ServerResponseException) {
            println("Error: ${e.response.status.description}")
            null
        } catch (e: Exception) {
            println("Error: ${e.message}")
            null
        }

}
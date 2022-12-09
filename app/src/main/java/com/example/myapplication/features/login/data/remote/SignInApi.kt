package com.example.myapplication.features.login.data.remote

import com.example.myapplication.features.login.data.remote.dto.AuthVerificationDto
import com.example.myapplication.features.login.data.remote.dto.UserCredentialDto
import com.example.myapplication.features.login.domain.model.AuthData
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.POST


interface SignInApi {

    @Headers("Content-Type:text/json")
    @POST("/api/LogIn")
    suspend fun signIn(@Body userCredentialDto: UserCredentialDto ): AuthData

    @Headers("Content-Type:text/json")
    @GET("/api/LogIn/SignOut")
    suspend fun signOut()

    @Headers("Content-Type:text/json")
    @POST("/api/LogIn/Verify")
    suspend fun verify(): AuthVerificationDto
}
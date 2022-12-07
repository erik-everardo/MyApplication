package com.example.myapplication.features.login.data.remote

import com.example.myapplication.features.login.data.remote.dto.SignInDto
import com.example.myapplication.features.login.domain.model.UserCredential
import retrofit2.http.Body
import retrofit2.http.Headers
import retrofit2.http.POST


interface SignInApi {

    @Headers("Content-Type:text/json")
    @POST("/api/LogIn")
    suspend fun signIn(@Body userCredential: UserCredential): SignInDto
}
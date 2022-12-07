package com.example.myapplication.features.login.data.remote.dto

data class SignInDto(
    val created: String,
    val expires: String,
    val token: String
)
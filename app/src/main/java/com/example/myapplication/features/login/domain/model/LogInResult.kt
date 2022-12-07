package com.example.myapplication.features.login.domain.model

data class LogInResult(
    val expires: String,
    val creation: String,
    val token: String
)

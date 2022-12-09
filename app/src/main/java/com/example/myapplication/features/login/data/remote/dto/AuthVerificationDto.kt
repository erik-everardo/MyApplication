package com.example.myapplication.features.login.data.remote.dto

data class AuthVerificationDto(
    val isValid: Boolean,
    val userId: Int
)
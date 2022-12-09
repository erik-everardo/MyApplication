package com.example.myapplication.features.login.presentation

data class SignInState(
    val validationError: Boolean,
    val success: Boolean?
)
package com.example.myapplication.features.login.domain.model

import com.example.myapplication.features.login.data.local.entity.AuthDataEntity

data class AuthData(
    val created: String,
    val expires: String,
    val token: String
) {
    fun toAuthDataEntity(): AuthDataEntity {
        return AuthDataEntity(created, expires, token)
    }
}
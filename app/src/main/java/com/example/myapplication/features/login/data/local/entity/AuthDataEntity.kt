package com.example.myapplication.features.login.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.myapplication.features.login.domain.model.AuthData

@Entity
data class AuthDataEntity(
    val created: String,
    val expires: String,
    val token: String,
    @PrimaryKey val id: Int? = null
) {
    fun toAuthData(): AuthData {
        return AuthData(created, expires, token)
    }
}

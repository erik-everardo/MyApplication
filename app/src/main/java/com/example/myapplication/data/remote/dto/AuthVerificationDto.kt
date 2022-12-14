package com.example.myapplication.data.remote.dto

data class AuthVerificationDto(
    val isValid: Boolean,
    val userId: Int
){
    override fun toString(): String {
        return "$isValid:$userId"
    }

    fun fromString(string: String): AuthVerificationDto {
        val split = string.split(":")
        return AuthVerificationDto(split[0].toBoolean(), split[1].toInt())
    }
}
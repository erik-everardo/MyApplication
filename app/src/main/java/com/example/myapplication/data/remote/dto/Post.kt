package com.example.myapplication.data.remote.dto

data class Post(
    val id: Int,
    val textContent: String,
    val userId: Int,
    val audioId: String?,
    val squadId: String?,
    val linkedDiscussionId: Int?,
    val linkedCommentId: Int?,
    val date: String?,
    val privacy: String,
    val username: String
)
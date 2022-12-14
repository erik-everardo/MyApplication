package com.example.myapplication.data.remote.dto

data class Comment(
    val audioId: String?,
    val date: String,
    val id: Int,
    val linkedCommentId: Int?,
    val linkedDiscussionId: Int?,
    val postId: Int,
    val responseForCommentId: Int?,
    val targetUser: Int,
    val textContent: String,
    val userId: Int
)
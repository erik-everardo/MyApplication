package com.example.myapplication.data.remote.dto

data class PostDataDto(
    val liked: Boolean,
    val numberOfComments: Int,
    val numberOfLikes: Int,
    val post: Post,
    val squadName: String,
    val userName: String
)
package com.example.myapplication.data.remote.dto

data class CommentFeedDto(
    val `data`: List<CommentData>,
    val moreContent: Boolean
)
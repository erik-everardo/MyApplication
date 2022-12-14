package com.example.myapplication.data.remote.dto

data class FeedDto(
    val `data`: List<PostDataDto>,
    val moreContent: Boolean
)
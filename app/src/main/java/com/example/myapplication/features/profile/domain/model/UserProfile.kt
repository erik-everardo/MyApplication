package com.example.myapplication.features.profile.domain.model

import com.example.myapplication.features.profile.data.local.entity.UserProfileEntity

data class UserProfile(
    val descriptionAudioId: String?,
    val descriptionText: String?,
    val email: String,
    val followingThisUser: Boolean,
    val id: Int,
    val isUserItself: Boolean,
    val name: String,
    val numberOfFollowers: Int,
    val numberOfFollowing: Int,
    val numberOfLikes: Int,
    val numberOfPosts: Int,
    val profileImageId: String?,
    val thisUserIsFollowingMe: Boolean
) {
    fun toUserProfileEntity(): UserProfileEntity {
        return UserProfileEntity(
            id,
            descriptionAudioId,
            descriptionText,
            email,
            followingThisUser,
            isUserItself,
            name,
            numberOfFollowers,
            numberOfFollowing,
            numberOfLikes,
            numberOfPosts,
            profileImageId,
            thisUserIsFollowingMe
        )
    }
}
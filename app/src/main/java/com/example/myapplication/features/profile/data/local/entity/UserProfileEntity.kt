package com.example.myapplication.features.profile.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.myapplication.features.profile.domain.model.UserProfile

@Entity
data class UserProfileEntity(
    @PrimaryKey val id: Int,
    val descriptionAudioId: String?,
    val descriptionText: String?,
    val email: String,
    val followingThisUser: Boolean,
    val isUserItself: Boolean,
    val name: String,
    val numberOfFollowers: Int,
    val numberOfFollowing: Int,
    val numberOfLikes: Int,
    val numberOfPosts: Int,
    val profileImageId: String?,
    val thisUserIsFollowingMe: Boolean
) {
    fun toUserProfile(): UserProfile {
        return UserProfile(
            id = id,
            descriptionAudioId = descriptionAudioId,
            descriptionText = descriptionText,
            email = email,
            followingThisUser = followingThisUser,
            isUserItself = isUserItself,
            name = name,
            numberOfFollowers = numberOfFollowers,
            numberOfFollowing = numberOfFollowing,
            numberOfLikes = numberOfLikes,
            numberOfPosts = numberOfPosts,
            profileImageId = profileImageId,
            thisUserIsFollowingMe = thisUserIsFollowingMe
        )
    }
}

package com.example.myapplication.features.profile.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy.REPLACE
import androidx.room.Query
import com.example.myapplication.features.profile.data.local.entity.UserProfileEntity

@Dao
interface UserProfileDao {
    @Query("SELECT * FROM UserProfileEntity WHERE id = :userId LIMIT 1")
    suspend fun getUserProfileById(userId: Int): UserProfileEntity?

    @Insert(onConflict = REPLACE)
    suspend fun insertUserProfile(userProfile: UserProfileEntity)

    @Query("SELECT * FROM UserProfileEntity WHERE isUserItself = 1")
    suspend fun getUserItSelf(): UserProfileEntity?
}
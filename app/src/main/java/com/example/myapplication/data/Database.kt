package com.example.myapplication.data

import androidx.room.RoomDatabase
import com.example.myapplication.features.login.data.local.entity.AuthDataEntity
import androidx.room.Database
import com.example.myapplication.features.login.data.local.dao.LogInDao
import com.example.myapplication.features.profile.data.local.dao.UserProfileDao
import com.example.myapplication.features.profile.data.local.entity.UserProfileEntity

@Database(
    entities = [AuthDataEntity::class, UserProfileEntity::class],
    version = 2
)
abstract class Database: RoomDatabase() {
    abstract val logInDao: LogInDao
    abstract val userProfileDao: UserProfileDao
}
package com.example.myapplication.data

import androidx.room.RoomDatabase
import com.example.myapplication.features.login.data.local.entity.AuthDataEntity
import androidx.room.Database
import com.example.myapplication.features.login.data.local.dao.LogInDao

@Database(
    entities = [AuthDataEntity::class],
    version = 1
)
abstract class Database: RoomDatabase() {
    abstract val logInDao: LogInDao
}
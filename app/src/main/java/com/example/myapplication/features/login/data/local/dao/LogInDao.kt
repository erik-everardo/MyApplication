package com.example.myapplication.features.login.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy.REPLACE
import androidx.room.Query
import com.example.myapplication.features.login.data.local.entity.AuthDataEntity


@Dao
interface LogInDao {
    @Insert(onConflict = REPLACE)
    suspend fun setAuthData(authData: AuthDataEntity)

    @Query("SELECT * FROM AuthDataEntity LIMIT 1")
    suspend fun getAuthData(): AuthDataEntity?

    @Query("DELETE FROM AuthDataEntity")
    suspend fun removeAuthData()
}
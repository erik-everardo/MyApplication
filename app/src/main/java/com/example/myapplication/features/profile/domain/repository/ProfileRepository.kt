package com.example.myapplication.features.profile.domain.repository

import com.example.myapplication.core.util.DataRetrieveResult
import com.example.myapplication.features.profile.domain.model.UserProfile
import kotlinx.coroutines.flow.Flow

interface ProfileRepository {
    suspend fun getProfile(userId: Int): Flow<DataRetrieveResult<UserProfile?>>
    fun getCurrentStoredUser(): Flow<UserProfile?>
//    suspend fun insertProfile(userProfile: UserProfile)
}
package com.example.myapplication.features.profile.data.repository

import com.example.myapplication.core.util.DataRetrieveResult
import com.example.myapplication.data.remote.WebApi
import com.example.myapplication.features.login.data.local.dao.LogInDao
import com.example.myapplication.features.profile.data.local.dao.UserProfileDao
import com.example.myapplication.features.profile.domain.model.UserProfile
import com.example.myapplication.features.profile.domain.repository.ProfileRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException

class ProfileRepositoryImpl(
    private val userProfileDao: UserProfileDao,
    private val authDataDao: LogInDao,
    private val webApi: WebApi
) : ProfileRepository {

    override suspend fun getProfile(userId: Int): Flow<DataRetrieveResult<UserProfile?>> = flow {
        var userProfile = userProfileDao.getUserProfileById(userId)
        val authData = authDataDao.getAuthData()

        authData?.token?.let {
            try{
                val profile = webApi.getProfile(userId, it)
                userProfileDao.insertUserProfile(profile.toUserProfileEntity())
                userProfile = userProfileDao.getUserProfileById(userId)

                emit(DataRetrieveResult.Success(userProfile?.toUserProfile()))
            } catch(e: HttpException) {
                emit(DataRetrieveResult.Error(
                    message = "Server error",
                    data = userProfile?.toUserProfile()))
            } catch(e: IOException) {
                emit(DataRetrieveResult.Error(
                    message = "Error connecting to the server",
                    data = userProfile?.toUserProfile()))
            }
        }


    }

    override fun getCurrentStoredUser(): Flow<UserProfile?>  = flow {
        emit(userProfileDao.getUserItSelf()?.toUserProfile())
    }


//    override suspend fun insertProfile(userProfile: UserProfile) {
//        userProfileDao.insertUserProfile(userProfile.toUserProfileEntity())
//    }
}
package com.example.myapplication.features.login.data.repository

import com.example.myapplication.core.util.DataRetrieveResult
import com.example.myapplication.data.remote.WebApi
import com.example.myapplication.features.login.data.local.dao.LogInDao
import com.example.myapplication.data.remote.dto.AuthVerificationDto
import com.example.myapplication.data.remote.dto.UserCredentialDto
import com.example.myapplication.features.login.domain.model.AuthData
import com.example.myapplication.features.login.domain.repository.LogInRepository
import com.example.myapplication.features.profile.data.local.dao.UserProfileDao
import com.example.myapplication.features.profile.domain.model.UserProfile
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException

class LogInRepositoryImpl(private val signInApi: WebApi, private val authDataDao: LogInDao, private val userProfileDao: UserProfileDao) : LogInRepository {

    override fun authenticateUserWithCredential(email: String, password: String): Flow<DataRetrieveResult<AuthData>> = flow {
        emit(DataRetrieveResult.Loading())

        try {
            val authData = signInApi.signIn(UserCredentialDto(email, password))
            authDataDao.removeAuthData()
            authDataDao.setAuthData(authData.toAuthDataEntity())

            emit(DataRetrieveResult.Success(authData))

        } catch(e: HttpException, ){
            emit(DataRetrieveResult.Error(message = "An error has occurred"))

        } catch(e: IOException){
            emit(DataRetrieveResult.Error(message = "No Internet connection available"))
        }
    }

    override fun getCurrentAuthData(): Flow<DataRetrieveResult<AuthData>> = flow {
        emit(DataRetrieveResult.Loading())

        val authData = authDataDao.getAuthData()
        if (authData != null) {
            emit(DataRetrieveResult.Success(data = authData.toAuthData()))
        } else {
            emit(DataRetrieveResult.Error(data = null, message = "Did not authenticate"))
        }
    }

    override fun validateAuthData(token: String): Flow<DataRetrieveResult<AuthVerificationDto>> = flow {
        try {

            val authVerificationData = signInApi.verify(token)

            emit(DataRetrieveResult.Success(authVerificationData))

        } catch(e: HttpException, ){
            emit(DataRetrieveResult.Error(message = "An error has occurred"))

        } catch(e: IOException){
            emit(DataRetrieveResult.Error(message = "No Internet connection available"))
        }
    }




//    override fun signOutUser(): Flow<SimpleDataRetrieveResult> = flow {
//        emit(DataRetrieveResult.Loading())
//
//        try {
//            signInApi.signOut()
//            authDataDao.removeAuthData()
//
//            emit(SimpleDataRetrieveResult)
//
//        } catch(e: HttpException, ){
//            emit(DataRetrieveResult.Error(message = "An error has occurred"))
//
//        } catch(e: IOException){
//            emit(DataRetrieveResult.Error(message = "No Internet connection available"))
//        }
//    }

    // Flujo de estado de sesión que UI esta viendo
    ///
    /// Implementar
    ///


}
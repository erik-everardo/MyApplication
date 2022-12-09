package com.example.myapplication.features.login.data.repository

import com.example.myapplication.core.util.DataRetrieveResult
import com.example.myapplication.core.util.SimpleDataRetrieveResult
import com.example.myapplication.features.login.data.local.dao.LogInDao
import com.example.myapplication.features.login.data.remote.SignInApi
import com.example.myapplication.features.login.data.remote.dto.UserCredentialDto
import com.example.myapplication.features.login.domain.model.AuthData
import com.example.myapplication.features.login.domain.repository.LogInRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException

class LogInRepositoryImpl(private val signInApi: SignInApi, private val authDataDao: LogInDao) : LogInRepository {

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
package com.example.myapplication.features.login.domain.repository

import com.example.myapplication.core.util.DataRetrieveResult
import com.example.myapplication.core.util.SimpleDataRetrieveResult
import com.example.myapplication.features.login.domain.model.AuthData
import kotlinx.coroutines.flow.Flow

interface LogInRepository {
    fun authenticateUserWithCredential(email: String, password: String): Flow<DataRetrieveResult<AuthData>>
    fun getCurrentAuthData(): Flow<DataRetrieveResult<AuthData>>
//    fun signOutUser(): Flow<SimpleDataRetrieveResult>


}
package com.example.myapplication.features.login.di

import android.app.Application
import com.example.myapplication.core.webapi.Constants.BASE_URL
import com.example.myapplication.data.Database
import com.example.myapplication.features.login.data.local.dao.LogInDao
import com.example.myapplication.features.login.data.remote.SignInApi
import com.example.myapplication.features.login.data.repository.LogInRepositoryImpl
import com.example.myapplication.features.login.domain.repository.LogInRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object LogInModule {

    @Provides
    @Singleton
    fun provideLogInRepository(api: SignInApi, db: Database): LogInRepository {
        return LogInRepositoryImpl(api, db.logInDao)
    }

    @Provides
    @Singleton
    fun provideSignInApi(): SignInApi {
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(SignInApi::class.java)
    }
}
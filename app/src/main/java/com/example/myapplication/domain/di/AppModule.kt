package com.example.myapplication.domain.di

import android.app.Application
import androidx.room.Room
import com.example.myapplication.core.webapi.Constants.BASE_URL
import com.example.myapplication.data.Database
import com.example.myapplication.data.remote.WebApi
import com.example.myapplication.data.remote.http_url.HttpUrlCon
import com.example.myapplication.features.login.data.repository.LogInRepositoryImpl
import com.example.myapplication.features.login.domain.repository.LogInRepository
import com.example.myapplication.features.profile.data.repository.ProfileRepositoryImpl
import com.example.myapplication.features.profile.domain.repository.ProfileRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.create
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {
    @Provides
    @Singleton
    fun provideLogInRepository(api: WebApi, db: Database): LogInRepository {
        return LogInRepositoryImpl(api, db.logInDao, db.userProfileDao)
    }

    @Provides
    @Singleton
    fun provideProfileRepository(db: Database, webApi: WebApi): ProfileRepository {
        return ProfileRepositoryImpl(db.userProfileDao, db.logInDao, webApi)
    }

    @Provides
    @Singleton
    fun provideDatabase(app: Application): Database {
        return Room.databaseBuilder(app, Database::class.java, "main_db")
            .fallbackToDestructiveMigration()
            .build()
    }

    @Provides
    @Singleton
    fun provideWebApi(): WebApi {
        return Retrofit
            .Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create()
    }

    @Provides
    @Singleton
    fun provideXmlApi(): HttpUrlCon {
        return HttpUrlCon()
    }
}
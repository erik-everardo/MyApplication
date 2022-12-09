package com.example.myapplication.domain.di

import android.app.Application
import androidx.room.Room
import com.example.myapplication.data.Database
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {
    @Provides
    @Singleton
    fun provideDatabase(app: Application): Database {
        return Room.databaseBuilder(app, Database::class.java, "main_db")
            .build()
    }
}
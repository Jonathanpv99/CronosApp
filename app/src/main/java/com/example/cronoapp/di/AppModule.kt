package com.example.cronoapp.di

import android.content.Context
import androidx.room.Room
import com.example.cronoapp.room.CronosDataBase
import com.example.cronoapp.room.CronosDatabaseDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class AppModule {

    @Singleton
    @Provides
    fun providesCronosDao(cronoDatabase: CronosDataBase): CronosDatabaseDao{
        return cronoDatabase.cronosDao()
    }

    @Singleton
    @Provides
    fun providesCronosDatabase(@ApplicationContext context: Context): CronosDataBase {
        return Room.databaseBuilder(
            context,
            CronosDataBase::class.java,
            name = "cronos_db"
        ).fallbackToDestructiveMigration()
            .build()
    }
}
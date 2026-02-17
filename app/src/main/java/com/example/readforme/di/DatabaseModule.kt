package com.example.readforme.di

import android.content.Context
import androidx.room.Room
import com.example.readforme.data.local.AppDatabase
import com.example.readforme.data.local.TextDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {
    @Provides
    @Singleton
    fun provideDatabase(
        @ApplicationContext context: Context
    ) : AppDatabase {
        return Room.databaseBuilder(
            context,
            AppDatabase::class.java,
            "readforme_database"
        ).build()
    }

    @Provides
    fun provideTextDao(database: AppDatabase): TextDao {
        return database.textDao()
    }
}
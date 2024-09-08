package com.example.database.di

import android.content.Context
import androidx.room.Room
import com.example.database.MusicDao
import com.example.database.MusicDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Singleton
    @Provides
    fun provideDataBase(@ApplicationContext context: Context): MusicDatabase {
        return Room.databaseBuilder(
            context.applicationContext,
            MusicDatabase::class.java,
            "music.db"
        )
            .fallbackToDestructiveMigration()
            .build()
    }

    @Provides
    fun provideMusicDao(database: MusicDatabase): MusicDao = database.musicDao()
}
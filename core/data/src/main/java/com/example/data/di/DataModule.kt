package com.example.data.di

import com.example.data.repository.MusicRepository
import com.example.data.repository.OfflineFirstMusicRepository
import com.example.data.repository.OfflineFirstUserDataRepository
import com.example.data.repository.UserDataRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class DataModule {

    @Binds
    internal abstract fun bindsMusicRepository(
        musicRepository: OfflineFirstMusicRepository,
    ): MusicRepository

    @Binds
    internal abstract fun bindsUserDataRepository(
        userDataRepository: OfflineFirstUserDataRepository,
    ): UserDataRepository
}
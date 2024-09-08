package com.example.data.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class DataModule {

    @Binds
    internal abstract fun bindsMusicRepository(
        musicRepository: com.example.data.repository.OfflineFirstMusicRepository,
    ): com.example.data.repository.MusicRepository

    @Binds
    internal abstract fun bindsUserDataRepository(
        userDataRepository: com.example.data.repository.OfflineFirstUserDataRepository,
    ): com.example.data.repository.UserDataRepository
}
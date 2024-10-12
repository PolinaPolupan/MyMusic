package com.example.mymusic.core.data.di

import com.example.mymusic.core.data.repository.MusicRepository
import com.example.mymusic.core.data.repository.OfflineFirstMusicRepository
import com.example.mymusic.core.data.repository.OfflineFirstUserDataRepository
import com.example.mymusic.core.data.repository.UserDataRepository
import com.example.mymusic.core.network.MyMusicNetworkDataSource
import com.example.mymusic.core.network.fake.FakeNetworkDataSource
import dagger.Binds
import dagger.Module
import dagger.hilt.components.SingletonComponent
import dagger.hilt.testing.TestInstallIn

@Module
@TestInstallIn(
    components = [SingletonComponent::class],
    replaces = [DataModule::class],
)
internal interface TestDataModule {

    @Binds
    fun bindsMusicRepository(
        musicRepository: OfflineFirstMusicRepository,
    ): MusicRepository

    @Binds
    fun bindsUserDataRepository(
        userDataRepository: OfflineFirstUserDataRepository,
    ): UserDataRepository

    @Binds
    fun bindsNetworkDataSource(
        networkDataSource: FakeNetworkDataSource,
    ): MyMusicNetworkDataSource
}
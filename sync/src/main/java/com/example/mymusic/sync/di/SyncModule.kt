package com.example.mymusic.sync.di

import com.example.mymusic.sync.OfflineFirstSyncManager
import com.example.mymusic.sync.SyncManager
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class SyncModule {

    @Binds
    abstract fun provideSyncManager(syncManager: OfflineFirstSyncManager): SyncManager
}
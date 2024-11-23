package com.example.mymusic.sync.di

import com.example.mymusic.sync.SyncManager
import com.example.mymusic.sync.fake.FakeSyncManager
import dagger.Binds
import dagger.Module
import dagger.hilt.components.SingletonComponent
import dagger.hilt.testing.TestInstallIn

@Module
@TestInstallIn(
    components = [SingletonComponent::class],
    replaces = [SyncModule::class]
)
abstract class TestSyncModule {

    @Binds
    abstract fun provideSyncManager(syncManager: FakeSyncManager): SyncManager
}

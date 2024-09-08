package com.example.sync.di

import android.content.Context
import com.example.sync.SyncManager
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object SyncModule {

    fun provideSyncManager(@ApplicationContext context: Context): SyncManager = SyncManager(context)
}
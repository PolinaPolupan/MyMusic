package com.example.mymusic.sync

import kotlinx.coroutines.flow.Flow

interface SyncManager {

    val isSyncing: Flow<Boolean>
    fun refresh()
}



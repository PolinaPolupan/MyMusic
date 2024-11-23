package com.example.mymusic.sync.fake

import android.util.Log
import com.example.mymusic.sync.SyncManager
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf
import javax.inject.Inject

class FakeSyncManager @Inject constructor(): SyncManager {

    override val isSyncing: Flow<Boolean> = flowOf(false)

    override fun refresh() {
        // Mock behavior for testing
        Log.i("FakeSyncManager", "Refresh called in test environment")
    }
}
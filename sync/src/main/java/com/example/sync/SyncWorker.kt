package com.example.sync

import android.content.Context
import android.util.Log
import androidx.hilt.work.HiltWorker
import androidx.work.CoroutineWorker
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.OutOfQuotaPolicy
import androidx.work.WorkerParameters
import com.example.auth.AuthorizationManager
import com.example.common.IoDispatcher
import com.example.data.repository.OfflineFirstMusicRepository
import com.example.data.repository.OfflineFirstUserDataRepository
import dagger.assisted.Assisted
import dagger.assisted.AssistedInject
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext


@HiltWorker
class SyncWorker @AssistedInject constructor(
    @Assisted private val appContext: Context,
    @Assisted workerParams: WorkerParameters,
    @IoDispatcher private val dispatcher: CoroutineDispatcher,
    private val authorizationManager: AuthorizationManager,
    private val musicRepository: OfflineFirstMusicRepository,
    userDataRepository: OfflineFirstUserDataRepository,
): CoroutineWorker(appContext, workerParams) {

    private val authState: Flow<String?> =
        userDataRepository.userPreferencesFlow
            .filterNotNull()
            .map {
                it.authState
            }

    override suspend fun doWork(): Result {
        // Indicate whether the work finished successfully with the Result
        return withContext(dispatcher) {
            try {
                Log.i("MainActivity", "Sync worker has begun work")

                authorizationManager.restoreState(authState.first())
                musicRepository.refresh()

                Result.success()

            } catch (throwable: Throwable) {
                Log.e("MainActivity", "Sync Worker failed", throwable)

                Result.failure()
            }
        }
    }

    companion object {
        /**
         * Expedited one time work to sync data on app startup
         */
        fun startUpSyncWork() = OneTimeWorkRequestBuilder<SyncWorker>()
            .setExpedited(OutOfQuotaPolicy.RUN_AS_NON_EXPEDITED_WORK_REQUEST)
            .build()
    }
}

package edu.uw.euglim9.dotify.manager

import android.content.Context
import androidx.work.*
import java.util.concurrent.TimeUnit

private const val DOTIFY_SYNC_WORK_TAG = "DOTIFY_SYNC_WORK_TAG"

class DotifySyncManager(context: Context) {

    private val workManger: WorkManager = WorkManager.getInstance(context)

    fun syncSongs() {
        val request = OneTimeWorkRequestBuilder<DotifySyncWorker>()
            .setInitialDelay(5, TimeUnit.SECONDS)
            .setConstraints(
                Constraints.Builder()
                    .setRequiredNetworkType(NetworkType.CONNECTED)
                    .build()
            )
            .addTag(DOTIFY_SYNC_WORK_TAG)
            .build()

        workManger.enqueue(request)
    }

    fun refreshRandomSongPeriodically() {
        if (isDotifySyncRunning()) {
            return
        }
        val request = PeriodicWorkRequestBuilder<DotifySyncWorker>(20, TimeUnit.MINUTES)
            .setInitialDelay(5, TimeUnit.SECONDS)
            .setConstraints(
                Constraints.Builder()
                    .setRequiredNetworkType(NetworkType.CONNECTED)
                    .build()
            )
            .addTag(DOTIFY_SYNC_WORK_TAG)
            .build()

        workManger.enqueue(request)
    }

    fun stopPeriodicallyRefreshing() {
        workManger.cancelAllWorkByTag(DOTIFY_SYNC_WORK_TAG)
    }

    fun extraCreditRefreshSongList() {
        if (isDotifySyncRunning()) {
            return
        }
        val request = PeriodicWorkRequestBuilder<DotifySyncWorker>(2, TimeUnit.DAYS)
            .setConstraints(
                Constraints.Builder()
                    .setRequiredNetworkType(NetworkType.CONNECTED)
                    .setRequiresBatteryNotLow(true)
                    .build()
            )
            .addTag(DOTIFY_SYNC_WORK_TAG)
            .build()

        workManger.enqueue(request)
    }

    private fun isDotifySyncRunning(): Boolean {
        return workManger.getWorkInfosByTag(DOTIFY_SYNC_WORK_TAG).get().any {
            when(it.state) {
                WorkInfo.State.RUNNING,
                WorkInfo.State.ENQUEUED -> true
                else -> false
            }
        }
    }

}

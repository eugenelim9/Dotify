package edu.uw.euglim9.dotify.manager

import android.content.Context
import android.util.Log
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.ericchee.songdataprovider.SongDataProvider
import edu.uw.euglim9.dotify.DotifyApplication
import java.lang.Exception

class DotifySyncWorker(
    private val context: Context,
    workerParameters: WorkerParameters
): CoroutineWorker(
    context, workerParameters) {

    private val application by lazy { context.applicationContext as DotifyApplication }
    private val dotifyNotificationManager by lazy { application.notificationManager }
    private lateinit var randomSong: com.ericchee.songdataprovider.Song

    override suspend fun doWork(): Result {
        return try {
            Log.i("DotifySyncWorker", "getting random song now")
            randomSong = SongDataProvider.getAllSongs().random()
            dotifyNotificationManager.publishNewSongNotification(randomSong)
            Result.success()
        } catch (ex: Exception) {
            Result.failure()
        }
    }
}
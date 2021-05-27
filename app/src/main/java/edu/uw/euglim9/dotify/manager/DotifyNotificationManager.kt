package edu.uw.euglim9.dotify.manager

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Build
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import com.ericchee.songdataprovider.Song
import edu.uw.euglim9.dotify.R
import edu.uw.euglim9.dotify.activity.PlayerActivity
import edu.uw.euglim9.dotify.activity.SONG_INFO_KEY
import kotlin.random.Random

private const val NEW_SONG_CHANNEL_ID = "NEW_SONG_CHANNEL_ID"

class DotifyNotificationManager(
    private val context: Context
) {

    private val notificationManager = NotificationManagerCompat.from(context)
    var isNotificationsEnabled = true

    init {
        initNotificationChannels()
    }

    fun publishNewSongNotification(song: Song) {
        if (!isNotificationsEnabled) {
            return
        }
        val intent = Intent(context, PlayerActivity::class.java).apply {
            flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            putExtra(SONG_INFO_KEY, song)
        }
        val pendingIntent: PendingIntent = PendingIntent.getActivity(context, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT)


        val builder = NotificationCompat.Builder(context, NEW_SONG_CHANNEL_ID)
            .setSmallIcon(R.drawable.ic_notification)
            .setContentTitle(context.getString(R.string.notification_title, song.artist))
            .setContentText(context.getString(R.string.notification_content, song.title))
            .setContentIntent(pendingIntent)
            .setAutoCancel(true)
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)

        with(notificationManager) {
            val notificationId = Random.nextInt()
            notify(notificationId, builder.build())
        }
    }

    private fun initNotificationChannels() {
        initNewSongChannel()
    }

    private fun initNewSongChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            // Info about the channel
            val name = context.getString(R.string.new_song)
            val descriptionText = context.getString(R.string.new_song_channel_description)
            val importance = NotificationManager.IMPORTANCE_DEFAULT

            // Create channel object
            val channel = NotificationChannel(NEW_SONG_CHANNEL_ID, name, importance).apply {
                description = descriptionText
            }

            // Tell the Android OS to create a channel
            notificationManager.createNotificationChannel(channel)
        }
    }
}
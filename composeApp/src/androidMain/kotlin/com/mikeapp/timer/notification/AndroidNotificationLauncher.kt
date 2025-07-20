package com.mikeapp.timer.notification

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.os.Build
import androidx.annotation.DrawableRes
import androidx.annotation.RequiresApi
import com.mikeapp.timer.MainActivity
import com.mikeapp.timer.R

object NotificationLauncher {
    @RequiresApi(Build.VERSION_CODES.O)
    fun notify(
        context: Context,
        title: String,
        msg: String,
        mute: Boolean = false,
        longMessageToPass: String? = null,
        @DrawableRes icon: Int = R.drawable.ic_notification_timer,
    ) {
        val notificationManager =
            context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager?
        val intent = Intent(context, MainActivity::class.java).apply {
            longMessageToPass?.let {
                putExtra("extra_long_string", longMessageToPass)
            }
        }

        val pendingIntent = PendingIntent.getActivity(
            context, 0, intent,
            PendingIntent.FLAG_IMMUTABLE or PendingIntent.FLAG_UPDATE_CURRENT
        )
        val channel = NotificationChannel(
            NOTIFICATION_CHANNEL_ID,
            NOTIFICATION_CHANNEL_NAME,
            NotificationManager.IMPORTANCE_HIGH
        )

        channel.enableLights(true)
        channel.lightColor = Color.GREEN
        channel.enableVibration(true)
        channel.setShowBadge(true)
        notificationManager?.createNotificationChannel(channel)
        val notificationBuilder = Notification.Builder(context)
            .setChannelId(NOTIFICATION_CHANNEL_ID)
            .setContentTitle(title)
            .setContentText(msg)
            .setAutoCancel(true)
            .setPriority(Notification.PRIORITY_HIGH)
            .setVibrate(longArrayOf())
            .setDefaults(Notification.DEFAULT_ALL)
            .setContentIntent(pendingIntent)
            .setSmallIcon(icon)
        if (mute) {
            notificationBuilder.setSound(null)
        }
        val notification = notificationBuilder.build()
        notificationManager?.notify(NOTIFICATION_ID, notification)
    }

    private const val NOTIFICATION_CHANNEL_ID = "my_channel_01"
    private const val NOTIFICATION_CHANNEL_NAME = "I'm channel name"
    private const val NOTIFICATION_ID = 111123
}

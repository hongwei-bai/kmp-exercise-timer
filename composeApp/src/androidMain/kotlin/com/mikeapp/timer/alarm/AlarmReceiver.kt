package com.mikeapp.timer.alarm

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.os.Build
import androidx.annotation.RequiresApi
import com.mikeapp.timer.notification.NotificationLauncher

class AlarmReceiver : BroadcastReceiver() {
    @RequiresApi(Build.VERSION_CODES.O)
    override fun onReceive(context: Context?, intent: Intent?) {
        context?.let {
            val title = intent?.getStringExtra("EXTRA_TITLE") ?: "Default Title"
            val message = intent?.getStringExtra("EXTRA_MESSAGE") ?: "Default Message"
            NotificationLauncher.notify(context, title, message)
        }
    }
}
package com.mikeapp.timer.alarm

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import com.mikeapp.timer.notification.NotificationLauncher

class AlarmReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context?, intent: Intent?) {
        context?.let {
            NotificationLauncher.notify(context, "111", "222")
        }
    }
}
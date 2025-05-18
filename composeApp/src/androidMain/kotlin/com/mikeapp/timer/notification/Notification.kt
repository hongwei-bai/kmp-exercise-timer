package com.mikeapp.timer.notification

import android.content.Context
import android.os.Build
import androidx.annotation.RequiresApi
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

actual object Notification : KoinComponent {
    private val context: Context by inject()

    @RequiresApi(Build.VERSION_CODES.O)
    actual fun showNotification(title: String, message: String) {
        NotificationLauncher.notify(
            context = context,
            title = title,
            msg = message
        )
    }
}
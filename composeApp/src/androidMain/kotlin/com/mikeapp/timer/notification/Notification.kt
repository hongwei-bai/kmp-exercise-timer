package com.mikeapp.timer.notification

import android.content.Context
import android.os.Build
import androidx.annotation.RequiresApi
import org.koin.core.component.KoinComponent

actual object Notification : KoinComponent {
    @RequiresApi(Build.VERSION_CODES.O)
    actual fun showNotification(title: String, message: String) {
        val koin = org.koin.core.context.GlobalContext.getOrNull()
        if (koin != null) {
            val context: Context = koin.get()
            NotificationLauncher.notify(
                context = context,
                title = title,
                msg = message
            )
        }
    }
}
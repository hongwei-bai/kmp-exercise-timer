package com.mikeapp.timer.notification

import org.koin.core.component.KoinComponent

actual object Notification : KoinComponent {
    actual fun showNotification(title: String, message: String, category: NotificationCategory) {
        TODO("Not yet implemented")
    }
}
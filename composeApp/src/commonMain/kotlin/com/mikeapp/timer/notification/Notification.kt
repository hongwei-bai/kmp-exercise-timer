package com.mikeapp.timer.notification

expect object Notification {
    suspend fun showNotification(title: String, message: String, category: NotificationCategory)
}
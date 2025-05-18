package com.mikeapp.timer.notification

expect object Notification {
    fun showNotification(title: String, message: String)
}
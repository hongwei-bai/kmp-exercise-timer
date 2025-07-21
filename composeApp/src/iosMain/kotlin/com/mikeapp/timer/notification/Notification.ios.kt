package com.mikeapp.timer.notification

import org.koin.core.component.KoinComponent
import platform.UserNotifications.*
import platform.Foundation.*
import platform.darwin.*
import kotlinx.cinterop.*

actual object Notification : KoinComponent {

    actual suspend fun showNotification(
        title: String,
        message: String,
        category: NotificationCategory
    ) {
        println("showNotification: $title, message: $message")
        val center = UNUserNotificationCenter.currentNotificationCenter()

        center.requestAuthorizationWithOptions(
            options = UNAuthorizationOptionAlert or UNAuthorizationOptionSound
        ) { granted, error ->
            println("Granted: $granted, Error: $error")
            if (granted) {
                // Create content (no `apply`, just assign directly)
                val content = UNMutableNotificationContent()
                content.setTitle(title)
                content.setBody(message)
                content.setSound(UNNotificationSound.defaultSound())

                // Trigger in 1 seconds (you can replace with a parameter if needed)
                val trigger = UNTimeIntervalNotificationTrigger.triggerWithTimeInterval(
                    timeInterval = 1.0,
                    repeats = false
                )

                val request = UNNotificationRequest.requestWithIdentifier(
                    identifier = NSUUID().UUIDString,
                    content = content,
                    trigger = trigger
                )

                center.addNotificationRequest(request) { error ->
                    error?.let { println("Failed to add notification: $it") }
                }
            } else {
                println("Notification permission denied or error: $error")
            }
        }
    }
}

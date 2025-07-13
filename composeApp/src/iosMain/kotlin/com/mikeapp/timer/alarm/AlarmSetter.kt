package com.mikeapp.timer.alarm

import platform.UserNotifications.*
import platform.Foundation.*
import platform.darwin.*
import kotlinx.cinterop.*

actual object AlarmSetter {

    private val center = UNUserNotificationCenter.currentNotificationCenter()
    private const val ALARM_ID = "MELON_TIMER_ALARM"

    actual fun setAlarm(
        timestampMillis: Long,
        title: String,
        message: String,
        alarmCategory: AlarmCategory
    ) {
        println("AlarmSetter.setAlarm($timestampMillis)")

        center.requestAuthorizationWithOptions(
            options = UNAuthorizationOptionAlert or UNAuthorizationOptionSound
        ) { granted, error ->
            if (granted) {
                val content = UNMutableNotificationContent()
                content.setTitle(title)
                content.setBody(message)
                content.setSound(UNNotificationSound.defaultSound())

                // Calculate delay in seconds (minimum 1.0)
                val nowMillis = (NSDate().timeIntervalSince1970 * 1000).toLong()
                val delaySeconds = maxOf((timestampMillis - nowMillis) / 1000.0, 1.0)

                val trigger = UNTimeIntervalNotificationTrigger.triggerWithTimeInterval(
                    timeInterval = delaySeconds,
                    repeats = false
                )

                val request = UNNotificationRequest.requestWithIdentifier(
                    identifier = ALARM_ID,
                    content = content,
                    trigger = trigger
                )

                center.addNotificationRequest(request) { addError ->
                    if (addError != null) {
                        println("❌ Failed to schedule alarm: $addError")
                    } else {
                        println("✅ Alarm scheduled for $delaySeconds seconds later")
                    }
                }
            } else {
                println("❌ Notification permission denied or error: $error")
            }
        }
    }

    actual fun cancelAlarm(
        title: String,
        message: String,
        alarmCategory: AlarmCategory
    ) {
        println("AlarmSetter.cancelAlarm()")
        center.removePendingNotificationRequestsWithIdentifiers(listOf(ALARM_ID))
        center.removeDeliveredNotificationsWithIdentifiers(listOf(ALARM_ID))
    }
}
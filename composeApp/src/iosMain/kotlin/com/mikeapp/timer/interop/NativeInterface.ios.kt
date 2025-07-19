package com.mikeapp.timer.interop

import androidx.room.Room
import com.mikeapp.timer.alarm.AlarmCategory
import com.mikeapp.timer.alarm.AlarmSound
import com.mikeapp.timer.alarm.toFileName
import com.mikeapp.timer.data.room.DB_FILE_NAME
import com.mikeapp.timer.data.room.TimerRoomDatabase
import com.mikeapp.timer.notification.NotificationCategory
import kotlinx.cinterop.ExperimentalForeignApi
import kotlinx.cinterop.ObjCAction
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import org.koin.core.component.KoinComponent
import platform.Foundation.*
import platform.UIKit.UIApplicationDidEnterBackgroundNotification
import platform.UIKit.UIApplicationWillEnterForegroundNotification
import platform.UserNotifications.*
import platform.darwin.NSObject
import platform.Foundation.NSURL
import org.koin.core.component.inject
import platform.darwin.NSObjectProtocol
import kotlin.native.ObjCName

private var retainedObserver: NSObject? = null

actual class NativeInterface {

    @OptIn(ExperimentalForeignApi::class)
    actual fun observeLifecycle(
        onEnterForeground: () -> Unit,
        onEnterBackground: () -> Unit
    ) {
        val observer = object : NSObject() {
            @ObjCAction
            fun handleEnterForeground() {
                println("🔄 AppLifecycle: enter foreground")
                onEnterForeground()
            }

            @ObjCAction
            fun handleEnterBackground() {
                println("🔄 AppLifecycle: enter background")
                onEnterBackground()
            }
        }

        val center = NSNotificationCenter.defaultCenter
        center.addObserver(
            observer,
            selector = NSSelectorFromString("handleEnterForeground"),
            name = UIApplicationWillEnterForegroundNotification,
            `object` = null
        )
        center.addObserver(
            observer,
            selector = NSSelectorFromString("handleEnterBackground"),
            name = UIApplicationDidEnterBackgroundNotification,
            `object` = null
        )

        retainedObserver = observer

        // ✅ First-time launch trigger
        println("🚀 AppLifecycle: first-time foreground trigger")
        onEnterForeground()
    }

    actual fun createRoomDatabase(): TimerRoomDatabase {
        val dbFile = "${fileDirectory()}/$DB_FILE_NAME"
        return Room
            .databaseBuilder<TimerRoomDatabase>(
                name = dbFile,
            ).setDriver(_root_ide_package_.androidx.sqlite.driver.bundled.BundledSQLiteDriver())
            .setQueryCoroutineContext(Dispatchers.IO)
            .build()
    }

    actual fun showNotification(
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

    @OptIn(ExperimentalForeignApi::class)
    private fun fileDirectory(): String {
        val documentDirectory: NSURL? = NSFileManager.defaultManager.URLForDirectory(
            directory = NSDocumentDirectory,
            inDomain = NSUserDomainMask,
            appropriateForURL = null,
            create = false,
            error = null,
        )
        return requireNotNull(documentDirectory).path!!
    }

    private val center = UNUserNotificationCenter.currentNotificationCenter()

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

    actual fun playSound(sound: AlarmSound) {
        println("playSound:")
        val name = NativeGreeter.greet("Mike")
        println("playSound: $name")
    }

    actual fun stopSound() {
    }

    companion object {
        private const val ALARM_ID = "MELON_TIMER_ALARM"
    }
}

object NativeInterfaceProvider : KoinComponent {
    val instance: NativeInterface by inject()
}

actual fun getNativeInterface(): NativeInterface {
    return NativeInterfaceProvider.instance
}

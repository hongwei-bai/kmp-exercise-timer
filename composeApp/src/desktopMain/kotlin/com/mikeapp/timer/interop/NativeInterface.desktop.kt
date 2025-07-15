package com.mikeapp.timer.interop

import androidx.room.Room
import androidx.sqlite.driver.bundled.BundledSQLiteDriver
import com.mikeapp.timer.alarm.AlarmCategory
import com.mikeapp.timer.data.room.DB_FILE_NAME
import com.mikeapp.timer.data.room.TimerRoomDatabase
import com.mikeapp.timer.notification.NotificationCategory
import kotlinx.coroutines.Dispatchers
import org.koin.core.context.GlobalContext
import java.awt.SystemTray
import java.awt.Toolkit
import java.awt.TrayIcon
import java.awt.TrayIcon.MessageType
import java.io.File

actual class NativeInterface {
    actual fun observeLifecycle(
        onEnterForeground: () -> Unit,
        onEnterBackground: () -> Unit
    ) {
        // No real backgrounding; you could add window focus tracking here if needed
        onEnterForeground()
        AppLifecycleHandlers.onEnterBackground = onEnterBackground
    }

    actual fun createRoomDatabase(): TimerRoomDatabase {
        val dbFile = File(System.getProperty("java.io.tmpdir"), DB_FILE_NAME)
        return Room.databaseBuilder<TimerRoomDatabase>(
            name = dbFile.absolutePath,
        ).fallbackToDestructiveMigrationOnDowngrade(
            dropAllTables = false
        ).setDriver(BundledSQLiteDriver())
            .setQueryCoroutineContext(Dispatchers.IO)
            .build()
    }

    actual fun showNotification(title: String, message: String, category: NotificationCategory) {
        showDesktopNotification(title, message)
    }

    actual fun setAlarm(timestampMillis: Long, title: String, message: String, alarmCategory: AlarmCategory) {
        println("AlarmSetter.setAlarm($timestampMillis)")
    }

    actual fun cancelAlarm(title: String, message: String, alarmCategory: AlarmCategory) {
        println("AlarmSetter.cancelAlarm()")
    }

    private fun showDesktopNotification(title: String, message: String) {
        if (!SystemTray.isSupported()) {
            println("System tray not supported!")
            return
        }

        val tray = SystemTray.getSystemTray()
        val image = Toolkit.getDefaultToolkit().createImage("")

        val trayIcon = TrayIcon(image, "App Notification").apply {
            isImageAutoSize = true
            toolTip = "App Notification"
        }

        try {
            tray.add(trayIcon)
            trayIcon.displayMessage(title, message, MessageType.INFO)
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }
}

object AppLifecycleHandlers {
    var onEnterBackground: () -> Unit = {}
}

actual fun getNativeInterface(): NativeInterface {
    return GlobalContext.get().get<NativeInterface>()
}
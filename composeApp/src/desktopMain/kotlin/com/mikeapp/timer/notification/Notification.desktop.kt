package com.mikeapp.timer.notification

import java.awt.SystemTray
import java.awt.Toolkit
import java.awt.TrayIcon
import java.awt.TrayIcon.MessageType

actual object Notification {
    actual suspend fun showNotification(title: String, message: String, category: NotificationCategory) {
        showDesktopNotification(title, message)
    }

    private suspend fun showDesktopNotification(title: String, message: String) {
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
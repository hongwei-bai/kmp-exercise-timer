package com.mikeapp.timer.interop

import com.mikeapp.timer.alarm.AlarmCategory
import com.mikeapp.timer.alarm.AlarmSound
import com.mikeapp.timer.data.room.TimerRoomDatabase
import com.mikeapp.timer.notification.NotificationCategory

expect class NativeInterface {
    fun observeLifecycle(onEnterForeground: () -> Unit, onEnterBackground: () -> Unit)

    fun createRoomDatabase(): TimerRoomDatabase

    fun setAlarm(timestampMillis: Long, title: String, message: String, alarmCategory: AlarmCategory)

    fun cancelAlarm(title: String, message: String, alarmCategory: AlarmCategory)

    fun showNotification(title: String, message: String, category: NotificationCategory)

    fun playSound(sound: AlarmSound)

    fun stopSound()
}

expect fun getNativeInterface(): NativeInterface


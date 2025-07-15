package com.mikeapp.timer.interop

import android.annotation.SuppressLint
import android.app.Application
import android.content.Context
import android.media.MediaPlayer
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.lifecycle.DefaultLifecycleObserver
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.ProcessLifecycleOwner
import androidx.room.Room
import androidx.sqlite.driver.bundled.BundledSQLiteDriver
import com.mikeapp.timer.R
import com.mikeapp.timer.alarm.AlarmCategory
import com.mikeapp.timer.alarm.AlarmHelper
import com.mikeapp.timer.alarm.AlarmSound
import com.mikeapp.timer.data.room.DB_FILE_NAME
import com.mikeapp.timer.data.room.TimerRoomDatabase
import com.mikeapp.timer.notification.NotificationCategory
import com.mikeapp.timer.notification.NotificationLauncher
import kotlinx.coroutines.Dispatchers
import org.koin.core.context.GlobalContext

actual class NativeInterface(
    private val app: Application
) {
    actual fun observeLifecycle(onEnterForeground: () -> Unit, onEnterBackground: () -> Unit) {
        val lifecycleOwner = ProcessLifecycleOwner.get()
        lifecycleOwner.lifecycle.addObserver(object : DefaultLifecycleObserver {
            override fun onResume(owner: LifecycleOwner) {
                onEnterForeground()
            }

            override fun onPause(owner: LifecycleOwner) {
                onEnterBackground()
            }
        })
    }

    actual fun createRoomDatabase(): TimerRoomDatabase {
        val dbFile = app.getDatabasePath(DB_FILE_NAME)
        return Room
            .databaseBuilder<TimerRoomDatabase>(
                context = app,
                name = dbFile.absolutePath,
            ).setDriver(BundledSQLiteDriver())
            .setQueryCoroutineContext(Dispatchers.IO)
            .build()
    }

    @SuppressLint("ScheduleExactAlarm")
    actual fun setAlarm(timestampMillis: Long, title: String, message: String, alarmCategory: AlarmCategory) {
        val koin = GlobalContext.getOrNull()
        if (koin != null) {
            val alarmHelper: AlarmHelper = koin.get()
            val requestCode =
                if (alarmCategory == AlarmCategory.Alarm) ALARM_REQUEST_CODE_ALARM else ALARM_REQUEST_CODE_REMINDER
            alarmHelper.setAlarm(timestampMillis, title, message, requestCode)
        }
    }

    actual fun cancelAlarm(title: String, message: String, alarmCategory: AlarmCategory) {
        val koin = GlobalContext.getOrNull()
        if (koin != null) {
            val alarmHelper: AlarmHelper = koin.get()
            val requestCode =
                if (alarmCategory == AlarmCategory.Alarm) ALARM_REQUEST_CODE_ALARM else ALARM_REQUEST_CODE_REMINDER
            alarmHelper.cancelAlarm(title, message, requestCode)
        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    actual fun showNotification(title: String, message: String, category: NotificationCategory) {
        val koin = org.koin.core.context.GlobalContext.getOrNull()
        if (koin != null) {
            val context: Context = koin.get()
            NotificationLauncher.notify(
                context = context,
                title = title,
                msg = message,
                icon = R.drawable.ic_notification_timer
            )
        }
    }

    private var mediaPlayer: MediaPlayer? = null

    actual fun playSound(sound: AlarmSound) {
        val resId = when (sound) {
            AlarmSound.Alarm_996 -> R.raw.alarm_996
            AlarmSound.Alarm_buzzer_992 -> R.raw.alarm_buzzer_992
        }

        mediaPlayer?.release()
        mediaPlayer = MediaPlayer.create(app, resId)
        mediaPlayer?.start()
    }

    actual fun stopSound() {
        mediaPlayer?.stop()
        mediaPlayer?.release()
        mediaPlayer = null
    }

    companion object {
        private const val ALARM_REQUEST_CODE_ALARM = 1001
        private const val ALARM_REQUEST_CODE_REMINDER = 1002
    }
}

actual fun getNativeInterface(): NativeInterface {
    return GlobalContext.get().get<NativeInterface>()
}
package com.mikeapp.timer.alarm

import android.annotation.SuppressLint
import org.koin.core.context.GlobalContext

@SuppressLint("ScheduleExactAlarm")
actual object AlarmSetter {
    actual suspend fun setAlarm(timestampMillis: Long, title: String, message: String, alarmCategory: AlarmCategory) {
        val koin = GlobalContext.getOrNull()
        if (koin != null) {
            val alarmHelper: AlarmHelper = koin.get()
            val requestCode =
                if (alarmCategory == AlarmCategory.Alarm) ALARM_REQUEST_CODE_ALARM else ALARM_REQUEST_CODE_REMINDER
            alarmHelper.setAlarm(timestampMillis, title, message, requestCode)
        }
    }

    actual suspend fun cancelAlarm(title: String, message: String, alarmCategory: AlarmCategory) {
        val koin = GlobalContext.getOrNull()
        if (koin != null) {
            val alarmHelper: AlarmHelper = koin.get()
            val requestCode =
                if (alarmCategory == AlarmCategory.Alarm) ALARM_REQUEST_CODE_ALARM else ALARM_REQUEST_CODE_REMINDER
            alarmHelper.cancelAlarm(title, message, requestCode)
        }
    }

    private const val ALARM_REQUEST_CODE_ALARM = 1001
    private const val ALARM_REQUEST_CODE_REMINDER = 1002
}
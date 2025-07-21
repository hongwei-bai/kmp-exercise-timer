package com.mikeapp.timer.alarm

// commonMain
expect object AlarmSetter {
    suspend fun setAlarm(timestampMillis: Long, title: String, message: String, alarmCategory: AlarmCategory)
    suspend fun cancelAlarm(title: String, message: String, alarmCategory: AlarmCategory)
}
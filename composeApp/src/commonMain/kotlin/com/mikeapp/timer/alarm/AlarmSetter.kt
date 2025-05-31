package com.mikeapp.timer.alarm

// commonMain
expect object AlarmSetter {
    fun setAlarm(timestampMillis: Long, title: String, message: String, alarmCategory: AlarmCategory)
    fun cancelAlarm(title: String, message: String, alarmCategory: AlarmCategory)
}
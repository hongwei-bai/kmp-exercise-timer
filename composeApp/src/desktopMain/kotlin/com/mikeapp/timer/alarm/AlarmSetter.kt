package com.mikeapp.timer.alarm

actual object AlarmSetter {
    actual suspend fun setAlarm(timestampMillis: Long, title: String, message: String, alarmCategory: AlarmCategory) {
        println("AlarmSetter.setAlarm($timestampMillis)")
    }

    actual suspend fun cancelAlarm(title: String, message: String, alarmCategory: AlarmCategory) {
        println("AlarmSetter.cancelAlarm()")
    }
}
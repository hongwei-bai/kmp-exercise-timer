package com.mikeapp.timer.data

data class TimerConfig(
    val reminderMinutes: Long,
    val alarmMinutes: Long,
    val isReminderMute: Boolean,
    val isAlarmMute: Boolean,
    val reminderState: String,
    val alarmState: String,
    val reminderTime: Long,
    val alarmTime: Long
)

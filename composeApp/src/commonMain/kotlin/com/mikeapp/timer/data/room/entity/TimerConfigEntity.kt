package com.mikeapp.timer.data.room.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "timer_config")
data class TimerConfigEntity(
    @PrimaryKey val id: Int = 1, // Always 1
    val reminderMinutes: Int,
    val alarmMinutes: Int,
    val isReminderMute: Boolean, // Originally 0/1, now stored as boolean
    val isAlarmMute: Boolean,
    val reminderState: String,
    val alarmState: String,
    val reminderTime: Long,
    val alarmTime: Long
)